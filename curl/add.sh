declare -r usage="Usage: $0 lanager-base-url lanager-cookie-value token video-url"
declare -r lanager_url="${1?-$usage}"
declare -r lanager_cookie="${2?-$usage}"
declare -r token="${3?-$usage}"
declare -r video="${4?-$usage}"
declare -r url="${lanager_url}/playlists/1/items"

curl \
    --referer "${url}" \
    --cookie "lanager=${lanager_cookie}" \
    --data-urlencode "_token=${token}" \
    --data-urlencode "url=${video}" \
    "${url}"
