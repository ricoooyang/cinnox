### Provide the callback of line
<details>
 <summary><code>POST</code> <code><b>/webhook-event</b></code></summary>
</details>

<br/>

### Query message list of the user
<details>
 <summary><code>GET</code> <code><b>/user/{userId}/message</b></code></summary>

##### Path Variables
  > | name      |  required     | data type               | description                                                           |
  > |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
  > | userId      |  true | String   | The user's line ID |

##### Query Parameters
  > | name      |  required     | data type               | description                                                           |
  > |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
  > | from      |  true | Number   | Query messages from the timestamp  |

##### Response
###### - <a href="https://developers.line.biz/en/reference/messaging-api/#message-event">Please refer to the official documentation of line</a>

</details>

<br/>

### Send a message to the user
<details>
 <summary><code>POST</code> <code><b>/user/{userId}/message</b></code></summary>

##### Path Variables
> | name      |  required     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | userId      |  true | String   | The user's line ID |

##### Request Body 
- Content Type: application/json
> | name      |  required     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | message   |  true | String   | Message to the user |

</details>

<br/>

### Get file of the message 
<details>
 <summary><code>GET</code> <code><b>/message/{messageId}/content</b></code></summary>


##### Path Variables
> | name      |  required     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | messageId      |  true | String   | The message ID from response of "GET /user/{userId}/message"|

#### Response body
- Content Type: application/cbor

</details>

    
  
