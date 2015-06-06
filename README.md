## run it

```sh
sbt ~reStart
```

## call it

```
curl -H "Accept: text/x-scriptfish" -s localhost:8080/key/kender
# kender
set -e user "kender"
set -e home "/home/kender"
```

```
curl -H "Accept: text/x-scriptsh" -s localhost:8080/key/kender
# kender
export user="kender"
export home="/home/kender"
```

```
curl -s localhost:8080/key/kender
{
  "key": "kender",
  "values": {
    "user": "kender",
    "home": "/home/kender"
  }
}
```
