# name : 테니스 센터 이름
# return : 검색한 사이트의 페이지 수
#          5페이지 넘어가는 경우에 대해서는 고려  X

import selenium.webdriver.chrome.webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys


def redirect_center_list(driver: selenium.webdriver.chrome.webdriver.WebDriver, name: str, url: str):
    driver.get(url)
    searching_box = driver.find_element(By.CSS_SELECTOR, '#search2 > fieldset > div.form_inp > input[type=text]')
    searching_box.send_keys(name)
    searching_box.send_keys(Keys.RETURN)