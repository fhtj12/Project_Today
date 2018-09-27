# 데이터 - 수집
from urllib import parse, request

from exception.error import error

# tag를 이용하여 가게 검색
def search_location_by_tag(tag, idx) :
    client_id = "Lb5e22EdmuuVX27PzgkG"
    client_pw = "TZOZTU41sQ"

    enc_text = parse.quote(tag) # 변수 tag를 URL 인코딩

    url = "https://openapi.naver.com/v1/search/local.json?query=" + enc_text + "&display=10&start=" + idx + "&sort=random"
        # API URL에 query와 패러미터를 추가한 url
    req = request.Request(url) # urllib.request.Request로 http 요청 객체 생성
    req.add_header("X-Naver-Client-Id", client_id)
    req.add_header("X-Naver-Client-Secret", client_pw) # header로 id와 secret 추가

    try :
        response = request.urlopen(req)
		# 객체를 매개변수로 request.urlopen을 호출해 Web 서버에 요청
    except :
        print("지역 {} 검색 불가".format(tag))
        return error.search_errors["invalid_hash_tag"]

    res_code = response.getcode() # response의 코드

    if (res_code == 200) : # 200 OK 이면
        response_body = response.read()
        try : 
            print("body " + response_body.decode('utf-8')) # 내용을 출력
            return error.common_errors["ok"]
        except : 
            return error.common_errors["parse_error"]