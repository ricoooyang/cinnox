package cinnox.filter;


import cinnox.LineMessageConfig;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class WebhookEventFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String signature = req.getHeader("x-line-signature");

        try {

            if (Strings.isBlank(signature)) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
            }

            CachedBodyHttpServletRequest cacheRequest = new CachedBodyHttpServletRequest(req);
            String requestBody = cacheRequest.getReader().lines().collect(Collectors.joining());
            if (!signature.equals(signature(requestBody))) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()));
            }

            chain.doFilter(cacheRequest, response);

        } catch (ResponseStatusException e) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(e.getStatusCode().value());
        }
    }

    private String signature(String httpRequestBody) {
        SecretKeySpec key = new SecretKeySpec(LineMessageConfig.CHANNEL_SECRET.getBytes(), "HmacSHA256");
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            byte[] source = httpRequestBody.getBytes("UTF-8");
            return Base64.encodeBase64String(mac.doFinal(source));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
