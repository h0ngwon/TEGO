import selenium.webdriver.chrome.webdriver
from selenium.webdriver.common.by import By


def page_count(driver: selenium.webdriver.chrome.webdriver.WebDriver) :
    return len(driver.find_elements(By.CSS_SELECTOR, '#contents > div:nth-child(9) > div > div > ul > li'))


def post_count(driver: selenium.webdriver.chrome.webdriver.WebDriver) :
    return len(driver.find_elements(By.CSS_SELECTOR, '#contents > div:nth-child(9) > ul > li'))
