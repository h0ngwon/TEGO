import time

from seoul.connection.seoul_login import seoul_login
from seoul.connection.redirect_center_list import redirect_center_list
from seoul.parse.parsing import parsing
from seoul.seoulService import count
from seoul.post import postJson
from seoul import centerInfo

from operator import itemgetter

from seoul.connection.set_chrome_driver import set_chrome_driver


tennis_reserve_page_url  = "https://yeyak.seoul.go.kr/web/search/selectPageListDetailSearchImg.do?code=T100&dCode=T108"

for center in centerInfo.centerList :
    driver = set_chrome_driver()

    # 테니스 센터 예약 화면 & 로그인
    seoul_login(driver, tennis_reserve_page_url) # 함수 종료 이후 테니스 센터 리스트 화면
    time.sleep(1)

    # 센터 검색, search_index = 검색어
    redirect_center_list(driver, center.get('search_index'), tennis_reserve_page_url)

    page_count = count.page_count(driver)     # 페이지 수

    # sorted : 파싱 해온 dic_list를 court, possibleTimeTable 순으로 정렬
    postJson.post_json(sorted(parsing(driver, page_count, center.get('before_char'), center.get('after_char')
                                      , center.get('search_index')), key=itemgetter('court', 'possibleTimeTable')))

    print(centerInfo.centerList.index(center) + 1, "/", len(centerInfo.centerList), "(", center.get('search_index'), ")")

    driver.quit()
