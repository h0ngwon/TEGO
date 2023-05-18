import {
  useSearchParams,
  useNavigate,
  createSearchParams,
} from "react-router-dom";
import { Row, Col, Typography, Input, Empty, Spin } from "antd";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { useQuery } from "react-query";
import CenterCard from "../components/CenterCard";
import { getCenterList } from "../api";

function Home() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const searchInput = searchParams.get("search");

  const [searchResult, setSearchResult] = useState([]);
  const [searchValue, setSearchValue] = useState();

  const { data, isLoading } = useQuery("centers", getCenterList);

  useEffect(() => {
    if (!data) {
      return;
    }

    const result = searchInput
      ? data.filter(center => center.name.includes(searchInput))
      : data;

    setSearchResult(result);
  }, [data, searchInput]);

  const searchOnChange = e => {
    setSearchValue(e.target.value);
  };

  const onSearch = () => {
    navigate({
      pathname: "/",
      search: createSearchParams({
        search: searchValue,
      }).toString(),
    });
  };

  return (
    <Row justify="space-between" align="middle">
      <Col span={24}>
        <Typography.Title level={2}>테니스 센터 목록</Typography.Title>
      </Col>
      <Col span={24}>
        <Col xl={8} xs={24} lg={12}>
          <SearchBar
            placeholder="테니스장 검색"
            size="large"
            enterButton
            onChange={searchOnChange}
            value={searchValue}
            onSearch={onSearch}
          />
        </Col>
      </Col>
      <Col span={24}>
        <CenterCardWrapper>
          {searchResult.map(center => (
            <CenterCard center={center} key={center.id} />
          ))}
          {searchResult.length === 0 && !isLoading && (
            <EmptyBox span={24}>
              <Empty
                description={
                  <Typography.Title level={4}>결과가 없습니다</Typography.Title>
                }
              />
            </EmptyBox>
          )}
          {isLoading && (
            <LoadingBox span={24}>
              <Spin size="large" />
            </LoadingBox>
          )}
        </CenterCardWrapper>
      </Col>
    </Row>
  );
}

const SearchBar = styled(Input.Search)`
  .ant-btn-primary {
    background-color: #4e9c81;
    border: none;
    &:hover,
    &:focus {
      background-color: rgba(78, 156, 129, 0.7);
    }
  }
`;

const CenterCardWrapper = styled(Row)`
  row-gap: 0px;
  margin-left: -32px;
  margin-right: -32px;
  @media only screen and (max-width: 575px) {
    margin-left: 0px;
    margin-right: 0px;
  }
`;

const EmptyBox = styled(Col)`
  padding-top: 200px;
`;

const LoadingBox = styled(Col)`
  padding-top: 200px;
  display: flex;
  justify-content: center;
`;

export default Home;
