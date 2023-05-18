# beforeChar : 테니스 코트 구분 인덱스의 이전 문자
# afterChar : 테니스 코트 구분 인덱스의 이후 문자

import selenium.webdriver.chrome.webdriver
import time

from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException

from seoul.seoulService import count
from seoul.parse.parsing_single_court import parsing_single_court


def parsing(driver: selenium.webdriver.chrome.webdriver.WebDriver,
            page_count: int, before_char: str, after_char: str, center_name: str):

    url_list = []  # url 중복 여부를 검사하기 위한 list
    dic_list = []  #

    for page in range(2, page_count + 2):  # 페이지

        time.sleep(1)
        post_count = count.post_count(driver)  # 페이지당 게시글 수

        # 각 post 파싱
        for post in range(1, post_count + 1):
            time.sleep(1)
            try:
                (court_identity, possible_time_list, return_url) \
                    = parsing_single_court(driver, '//*[@id="contents"]/div[3]/ul/li[' + str(post) + ']/a', before_char, after_char)
            except TypeError or NoSuchElementException:                   # 예약 마감 버튼
                driver.execute_script("window.history.go(-1)")      # 뒤로가기
            else:                               # 예약 가능 버튼
                if return_url in url_list:  # 이미 등록 되어 있는 코트 번호
                    for dic in dic_list:         # saveArray 안에
                        if dic.get('return_url') == return_url:      # court 키의 value 값이 동일한 딕셔너리를 찾으면
                            for possibleTime in possible_time_list:  # 내가 새로 가져온 예약가능시간리스트 내용들을
                                dic.get('possibleTimeTable').append(possibleTime)  # 해당 딕셔너리의 possibleTimeTable Key의 value에 새로 가져온 리스트 항목들을 추가
                else :
                    url_list.append(return_url)
                    dic_list.append(
                        {"url": return_url, "centerName": center_name, "court": court_identity, "possibleTimeTable": possible_time_list})
                driver.execute_script("window.history.go(-1)")      # 뒤로가기 두 번
                time.sleep(2)
                driver.execute_script("window.history.go(-1)")  # 뒤로가기 두 번

        # 페이지 넘기기
        if page <= page_count :
            driver.find_element(By.XPATH, '//*[@id="contents"]/div[3]/div/div/ul/li[' + str(page) + ']').click()

    return dic_list
