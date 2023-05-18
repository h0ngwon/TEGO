# 파싱 후 테니스 코트 리스트 페이지로 돌려보내는 것까지
# 돌려주는 건 '코트 번호' : '예약 가능 시간대' 형태의 dict

import selenium.webdriver.chrome.webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.by import By
import calendar


def parsing_single_court(driver: selenium.webdriver.chrome.webdriver.WebDriver,
                         post_xpath: str, before_char: str, after_char: str):

    possible_time_table = []

    driver.find_element(By.XPATH, post_xpath).click()

    # 팝업 있고 없고 확인
    if len(driver.find_element(By.CSS_SELECTOR, '#contents > div.pop_wrap.pop_board').get_attribute('style')) < 5:
        pass
    else:
        driver.find_element(
            By.CSS_SELECTOR, '#contents > div.pop_wrap.pop_board > div > div.btn_box > button > span').click()  # 팝업 지우기

    return_url = driver.current_url

    # 예약버튼 클릭
    try:
        driver.find_element(
            By.CSS_SELECTOR, '#aform > div.dt_top_box > div.con_box > div > div > a.common_btn.blue').click()
    except:
        return

    # if driver.find_element(By.CSS_SELECTOR, '#aform > div.dt_top_box > div.con_box > div > div > a').get_attribute(
    #         'class').strip() == 'common_btn white':
    #     return  # 예약 마감이면 함수 종료
    # driver.find_element(By.CSS_SELECTOR,
    #                     '#aform > div.dt_top_box > div.con_box > div > div > a.common_btn.blue').click()

    # 코트네임
    court_name = driver.find_element(By.CSS_SELECTOR, '#aform > h4').text
    if driver.find_element(By.CSS_SELECTOR, '#aform > h4').text.isspace() :
        court_name = driver.find_element(
            By.CSS_SELECTOR, '#aform > div.book_box > div.mob_info_wrap > div > div.book_info').text

    # 코트네임에서 코트번호만 따오기
    if before_char == '' and after_char == '':
        court_identity = '1'
    elif after_char == '':
        court_identity = court_name[court_name.find(before_char) + 1]
    else:
        court_identity = court_name[court_name.find(before_char) + 1: court_name.find(after_char)].strip()

    # 계남 예외
    if court_identity[0:2] == '실내':
        court_identity = court_identity[2]

    # 달력 상단의 'OOOO년 OO월'에서 연도와 월을 저장
    year_and_month = driver.find_element(By.CSS_SELECTOR, '#calendar > div > span').text
    year = year_and_month[0:4]
    month = year_and_month[6:8]

    # 월의 초일부터 말일까지
    for i in range(calendar.monthrange(int(year), int(month))[1]):
        day = i+1
        if driver.find_element(By.ID, 'calendar_2022' + month.zfill(2) + str(day).zfill(2)).get_attribute('class').strip() == 'able':
            driver.find_element(By.ID, 'calendar_2022' + month.zfill(2) + str(day).zfill(2)).click()        # day click
            time_table = driver.find_elements(By.CSS_SELECTOR, '#calendar_tm_area > div > ul > li')          # timetable

            for j in time_table:
                if j.text.split(' ')[1].find('0') >= 0:
                    possible_time_table.append(year + '-' + month.zfill(2) + '-' + str(day).zfill(2) + 'T' + j.text.split(' ')[0][0:2] + ':00:00')

    # 달력에 다음 달로 넘어가는 화살표가 있으면,
    try:
        driver.find_element(By.CSS_SELECTOR, '#calendar > div > button').click()
    except NoSuchElementException:
        pass
    else:
        month = str(int(month) + 1)
        # 월의 초일부터 말일까지
        for i in range(calendar.monthrange(int(year), int(month))[1]):
            day = i + 1
            try:
                if driver.find_element(By.ID, 'calendar_2022' + month.zfill(2) + str(day).zfill(2)).get_attribute('class').strip() == 'able':
                    driver.find_element(By.ID, 'calendar_2022' + month.zfill(2) + str(day).zfill(2)).click()  # day click
                    time_table = driver.find_elements(By.CSS_SELECTOR, '#calendar_tm_area > div > ul > li')  # timetable

                    for j in time_table:
                        if j.text.split(' ')[1].find('0') >= 0:
                            possible_time_table.append(
                                year + '-' + month.zfill(2) + '-' + str(day).zfill(2) + 'T' + j.text.split(' ')[0][0:2] + ':00:00')
            except NoSuchElementException:
                pass

    return court_identity, possible_time_table, return_url
