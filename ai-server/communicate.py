# 메인 - 통신

import http.server

from exception.error import error

class Handler(http.server.BaseHTTPRequestHandler) : 
    def do_GET(self) : 
        # 요청이 get일 때 실행되고, 응답 메세지를 전송한다.
        self.send_response(200)
        self.wfile.write(bytes(error.common_errors["ok"], 'utf-8'))

# 요청받을 주소
address = ('127.0.0.1', 19503)

# 요청 대기
listener = http.server.HTTPServer(address, Handler)
print(f'http://{address[0]}:{address[1]} 주소에서 요청 대기중...')
listener.serve_forever()