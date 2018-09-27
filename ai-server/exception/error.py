# 예외처리용 파일

class error : 
    # error 정의 딕셔너리
    common_errors = {
        'invalid_parameter' : 'invalid_parameter',
        'mysql_db_error' : 'mysql_db_error',
        'internal_error' : 'internal_error',
        'permission_error' : 'permission_error',
        'processing_error' : 'processing_error', 
        'parse_error' : 'parse_error',
        'ok' : 'ok'
    }
    search_errors = {
        'invalid_hash_tag' : 'invalid_hash_tag'
    }