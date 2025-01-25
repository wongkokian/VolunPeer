import React, { Fragment, useEffect, useState } from "react";
import { GET_ALL_PEERS } from "@/utils/constants/path";
import Peer from "./Peer";

function Peers() {
  const [peers, setPeers] = useState();
  useEffect(() => {
    async function getAllPeers() {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}${GET_ALL_PEERS}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            token: sessionStorage.getItem("token"),
          },
        }
      );
      const peersData = await response.json();
      console.log(peersData);
      if (peersData.returnCode == 200) {
        setPeers(peersData.connections);
      }
    }
    getAllPeers();
  }, []);

  return (
    <div style={{ marginTop: "3rem" }}>
      {peers &&
        peers.map((peer) => {
          return <Peer key={peer.name} name={peer.name}></Peer>;
        })}
    </div>
  );
}

export default Peers;
