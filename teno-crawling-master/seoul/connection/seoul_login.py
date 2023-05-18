# 로그인 처리
# 로그인 이후에는 테니스 예약 기본 화면으로 돌아감
# url : 로그인 종료 이후 돌아갈 페이지

import selenium.webdriver.chrome.webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys


def seoul_login(driver: selenium.webdriver.chrome.webdriver.WebDriver, url: str):
    driver.get(url)
    # 로그인 페이지로 이동
    driver.find_element(By.XPATH, '//*[@id="header"]/div[1]/div/div[1]/a').click()
    user_id = 'garamminchan'
    user_pw = 'cksrl0310~'

    id_box = driver.find_element(By.CSS_SELECTOR, '#userid')
    pw_box = driver.find_element(By.CSS_SELECTOR, '#userpwd')
    id_box.send_keys(user_id)
    pw_box.send_keys(user_pw)
    pw_box.send_keys(Keys.RETURN) # 로그인 종료 이후에 테니스 예약 화면으로 돌아감
