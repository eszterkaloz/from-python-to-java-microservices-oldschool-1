# Video Search Service


## Summary

This service searches for videos on multiple video sharing sites (currently YouTube and Vimeo), picks one for the categories 'review' and 'unboxing' and returns their embed codes in JSON.

## Goal

Our goal was to create a service which can find embed codes based on product names, so that these codes can be easily embedded to a webshop, where customers can watch product reviews without ever leaving the site.

## Usage

### Request

Each HTTP request consists of an HTTP Method, an endpoint and a list of parameters. These requests are formatted like the following example:

`GET https:0.0.0.0:60000/apivideos?search=lenovo thinkpad`

  - Method: `GET`
  - Endpoint: `/apivideos`
  - Parameter (required): `search`, more than 2 character long value
  
HTTP request example:
```
GET /apivideos?search=lenovo thinkpad HTTP/1.1
Host: 0.0.0.0:60000
Cache-Control: no-cache
```

cURL request example:
```
curl -X GET -H "Cache-Control: no-cache" "http://0.0.0.0:60000/apivideos?search=lenovo%20thinkpad"
```

### Response

The service sends a JSON response. For each video object it specifies its provider, search string as `title`, the category of the video and an embed code.

Example response:

```
[
  {
    "provider": "youtube",
    "title": "lenovo thinkpad",
    "category": "unboxing",
    "embed code": "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/bbvVof_bkmc\" frameborder=\"0\" allowfullscreen></iframe>"
  },
  {
    "provider": "vimeo",
    "title": "lenovo thinkpad",
    "category": "unboxing",
    "embed code": "<iframe src=\"https://player.vimeo.com/video/101697648?title=0&byline=0&portrait=0&badge=0&autopause=0&player_id=0\" width=\"1280\" height=\"720\" frameborder=\"0\" title=\"Lenovo Thinkpad 2 Unboxing [Deutsch]\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>"
  },
  {
    "provider": "youtube",
    "title": "lenovo thinkpad",
    "category": "review",
    "embed code": "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/1d-v0vLLM7w\" frameborder=\"0\" allowfullscreen></iframe>"
  },
  {
    "provider": "vimeo",
    "title": "lenovo thinkpad",
    "category": "review",
    "embed code": "<iframe src=\"https://player.vimeo.com/video/14549845?title=0&byline=0&portrait=0&badge=0&autopause=0&player_id=0\" width=\"1280\" height=\"720\" frameborder=\"0\" title=\"Lenovo ThinkPad x201 Review\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>"
  }
]
```

### Errors

If the query parameter is omitted altogether, the following response is produced:
```
{
  "error": {
    "error_type": "Unexpected error occurred",
    "error_code": 500
  }
}
```

If the search string is less than 3 characters long, it is considered to be too short for usage, and the following warning is returned:
```
{
  "error_type": "Bad request. Request parameter is missing or too low?",
  "error_code": 400
}
```

## Install and config

### Installation

The project can be cloned from GitHub by issuing the command 
`git clone https://github.com/CodecoolBP20161/from-python-to-java-microservices-oldschool-1.git`, 
and opened by IntelliJ IDEA. The entry point of the service is the `Servlet` class.

### Configuration

The required port where the service is to be launched can be specified as the first command line argument. If omitted the port defaults to `60000`.
