# Incode Challenge

## Methods

#### GET http://localhost:8080/backend-service?verificationId={verificationId}&query={query}
##### Example (request): http://localhost:8080/backend-service?verificationId=10125f63-967c-4d46-9e7e-52168e9a4a3b&query=2
##### Example (response): 
```json 
    [
  {
    "verificationId": "b1ae5eec-6999-4314-a943-8b6eb5de091b",
    "responseJson": {
      "verificationId": "b1ae5eec-6999-4314-a943-8b6eb5de091b",
      "query": "1",
      "source": "FREE",
      "result": {
        "id": "JP16R1H4",
        "name": "Browning, Hall and Stewart",
        "registrationDate": "2024-04-21",
        "address": "782 Jones Forks Apt. 304, Joshuaville, TN 33363"
      },
      "otherResults": [
        {
          "id": "1Q7N5CUX",
          "name": "Vaughn, Cortez and Ross",
          "registrationDate": "2021-04-30",
          "address": "041 Jackson Courts, North John, ME 29490"
        },
        {
          "id": "IY1F6P49",
          "name": "Gibbs, Wilson and Santiago",
          "registrationDate": "2022-03-10",
          "address": "989 Evelyn Terrace Apt. 791, Jamesfort, MN 44140"
        }
      ],
      "message": "Source: FREE"
    },
    "created": "2026-06-04T16:49:45.398208",
    "id": 1
  }
]
```

#### GET http://localhost:8080/free-third-party?query={query}
##### Example: http://localhost:8080/free-third-party?query=W

#### GET http://localhost:8080/premium-third-party?query={query}
##### Example: http://localhost:8080/premium-third-party?query=W

## Review requests by verificationId
#### GET http://localhost:8080/logs/api/request/{verificationId}
##### Example (request): http://localhost:8080/logs/api/request/b1ae5eec-6999-4314-a943-8b6eb5de091b

### UI to search by verificationId
#### http://localhost:8080/logs/view
![image_alt](https://github.com/saulhuerta/incode/blob/2f3b0c811aae6db4687f6f1de0944b8c86c203b7/incode-logs.png)

### Video
https://github.com/saulhuerta/incode/blob/8cd3ac606c16befc8f300deb6eb39cb98a2d29b6/Incode.mp4

