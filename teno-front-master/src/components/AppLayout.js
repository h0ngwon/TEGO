import { useState } from "react";
import { Layout, Row, Col, Button, Affix, Drawer, Typography } from "antd";
import { MenuOutlined } from "@ant-design/icons";
import Text from "antd/lib/typography/Text";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { tokenStore } from "../util/token";

function AppLayout({ children }) {
  const { isLoggedIn } = tokenStore;
  const [isOpened, setIsOpened] = useState(false);

  const logOut = () => {
    localStorage.removeItem("user");
    window.location.reload();
  };

  const menuOpen = () => {
    setIsOpened(!isOpened);
  };

  return (
    <AppContainer>
      <FixedWrapper>
        <NavHeader>
          <Row justify="center" align="middle">
            <Col md={18} xs={21}>
              <Link to="/">
                <Logo strong>테니스고</Logo>
              </Link>
              <NavButtonWrapper>
                {!isLoggedIn && (
                  <div>
                    <MenuWrapper>
                      <Link to="/login">
                        <NavButton type="link">로그인</NavButton>
                      </Link>
                      <Link to="/signup">
                        <NavButton type="link">회원가입</NavButton>
                      </Link>
                    </MenuWrapper>
                    <MenuMdSize>
                      <MdMenuButton
                        onClick={menuOpen}
                        icon={<MenuOutlined />}
                        type="link"
                      />
                      <Drawer
                        placement="right"
                        onClose={menuOpen}
                        visible={isOpened}
                        zIndex={9999}
                      >
                        <Link to="/login">
                          <MdMenuButton type="link" onClick={menuOpen}>
                            로그인
                          </MdMenuButton>
                        </Link>
                        <br />
                        <Link to="/signup">
                          <MdMenuButton type="link" onClick={menuOpen}>
                            회원가입
                          </MdMenuButton>
                        </Link>
                      </Drawer>
                    </MenuMdSize>
                  </div>
                )}
                {isLoggedIn && (
                  <div>
                    <MenuWrapper>
                      <Link to="/profile">
                        <NavButton type="link">마이페이지</NavButton>
                      </Link>
                      <NavButton type="link" onClick={logOut}>
                        로그아웃
                      </NavButton>
                    </MenuWrapper>
                    <MenuMdSize>
                      <MdMenuButton
                        type="link"
                        onClick={menuOpen}
                        icon={<MenuOutlined />}
                      />
                      <Drawer
                        placement="right"
                        onClose={menuOpen}
                        visible={isOpened}
                        zIndex={9999}
                      >
                        <Link to="/profile">
                          <MdMenuButton type="link" onClick={menuOpen}>
                            마이페이지
                          </MdMenuButton>
                        </Link>
                        <br />
                        <MdMenuButton type="link" onClick={logOut}>
                          로그아웃
                        </MdMenuButton>
                      </Drawer>
                    </MenuMdSize>
                  </div>
                )}
              </NavButtonWrapper>
            </Col>
          </Row>
        </NavHeader>
      </FixedWrapper>
      <Row justify="center">
        <Col md={18} xs={21}>
          <ContentWrapper>{children}</ContentWrapper>
        </Col>
      </Row>
    </AppContainer>
  );
}

const AppContainer = styled(Layout)`
  background-color: #fff;
  width: 100%;
`;

const FixedWrapper = styled(Affix)`
  z-index: 9999;
`;

const ContentWrapper = styled(Layout.Content)`
  padding: 20px 0;
`;

const NavHeader = styled(Layout.Header)`
  background-color: #fff;
  border-bottom: 1px solid #f0f0f0;
  padding: 0;
  box-shadow: 0 4px 4px -4px rgba(0, 0, 0, 0.1);
`;

const MenuWrapper = styled.div`
  @media only screen and (max-width: 769px) {
    display: none;
  }
`;

const MenuMdSize = styled.div`
  @media only screen and (min-width: 769px) {
    display: none;
  }
`;

const Logo = styled(Text)`
  float: left;
  line-height: inherit;
  font-size: 2rem;
`;

const NavButtonWrapper = styled.div`
  float: right;
  vertical-align: middle;
`;

const NavButton = styled(Button)`
  outline: none;
  color: #000;
  font-size: 1rem;
  height: 64px;
  font-weight: bold;
  z-index: 9999;
  position: relative;
  border: none;

  ::after {
    content: "";
    height: 3px;
    width: 0;
    background-color: #8dc3a7;
    border-radius: 5px;
    position: absolute;
    left: 0;
    bottom: 0;
  }
  &:hover::after {
    width: 100%;
    background-color: #4e9c81;
  }

  &:hover {
    color: #4e9c81;
  }

  &:focus {
    color: #4e9c81;
  }
`;

const MdMenuButton = styled(Button)`
  outline: none;
  color: #000;
  font-size: 1rem;
  height: 64px;
  font-weight: bold;
`;

export default AppLayout;
