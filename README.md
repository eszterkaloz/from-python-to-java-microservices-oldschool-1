# Video Search Service

## Goal

Our goal was to create a service which can find embed codes based on product names, so that these codes can be easily embedded to a webshop, where customers can watch product review without ever leaving the site.

## Summary

This service searches for videos on multiple video sharing sites (currently YouTube and Vimeo), picks one for the categories 'review' and 'unboxing' and returns their embed codes in JSON.

## Usage

### Request

Each HTTP request consists of an HTTP Method, an endpoint and a list of parameters. These requests are formatted like the following example:

`GET https:0.0.0.0:60000/apivideos?search=lenovo thinkpad`

  - Method: `GET`
  - Endpoint: `/apivideos`
  - Parameter (required): `search`
  
HTTP request example:
```
GET /apivideos?search=lenovo thinkpad HTTP/1.1
Host: 0.0.0.0:60000
Cache-Control: no-cache
Postman-Token: 96b56ed6-46c8-c4ab-36df-44f4b1eb3adc
```

cURL request example:
```
curl -X GET -H "Cache-Control: no-cache" -H "Postman-Token: 504b5dea-5697-e6af-24ef-40afbdef1abd" "http://0.0.0.0:60000/apivideos?search=lenovo%20thinkpad"
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