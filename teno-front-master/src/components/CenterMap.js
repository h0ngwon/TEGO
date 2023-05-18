import { useEffect, useState } from "react";
import { NaverMap, Marker } from "react-naver-maps";
import styled from "styled-components";

function CenterMap({ centerAddress }) {
  const [coordinate, setCoordinate] = useState({});
  const [address, setAddress] = useState(centerAddress);
  const navermaps = window.naver.maps;

  useEffect(() => {
    navermaps.Service.geocode(
      {
        query: address,
      },
      function naverMap(status, response) {
        if (status !== navermaps.Service.Status.OK) {
          return <p>지도를 불러오는데 실패했습니다.</p>;
        }

        const result = response.v2;
        const items = result.addresses[0];
        return setCoordinate({ lng: items.x, lat: items.y });
      },
    );
  }, [address]);

  return (
    <TennisCenterMap mapDivId="map" center={coordinate} defaultZoom={15}>
      <Marker position={new navermaps.LatLng(coordinate)} />
    </TennisCenterMap>
  );
}

const TennisCenterMap = styled(NaverMap)`
  width: 100%;
  height: 250px;
  border-radius: 10px;
  border: 1px solid #d9d9d9;
`;

export default CenterMap;
