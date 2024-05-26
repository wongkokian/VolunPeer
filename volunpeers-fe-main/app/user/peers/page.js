"use client";

import React, { Fragment, useState } from "react";
import styles from "./page.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import Cards from "@/app/components/cards/Cards";
import Peers from "./components/Peers";
import Peer from "./components/Peer";
import {
  LIST_POTENTIAL_PEERS,
  QUESTS_YOUR_PEERS_ARE_ATTENDING,
} from "@/utils/constants/path";

function PeersPage() {
  const [selectedTab, setSelectedTab] = useState("Your VolunPeers");
  const [potentialPeers, setPotentialPeers] = useState();
  const [peerQuests, setPeerQuests] = useState();

  const tabs = [
    "Your VolunPeers",
    "Add Peers",
    "Quests Your Peers Are Attending",
  ];
  const handleTabClick = (tab) => {
    setSelectedTab(tab);
    if (tab == "Add Peers") {
      getPotentialPeers();
    }
    if (tab == "Quests Your Peers Are Attending") {
      getQuestsYourPeersAreAttending();
    }
  };
  async function getPotentialPeers() {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}${LIST_POTENTIAL_PEERS}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          token: sessionStorage.getItem("token"),
        },
      }
    );
    const potentialPeers = await response.json();
    if (potentialPeers.returnCode == 200) {
      setPotentialPeers(potentialPeers.potentialConnections);
    }
  }
  async function getQuestsYourPeersAreAttending() {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}${QUESTS_YOUR_PEERS_ARE_ATTENDING}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          token: sessionStorage.getItem("token"),
        },
      }
    );
    const data = await response.json();
    if (data.returnCode == 200) {
      setPeerQuests(data.quests);
    }
  }
  return (
    <div className={`${styles.peers__container}`}>
      <div className={`${styles.peers__content}`}>
        <span className={`${textStyles.xl1} ${textStyles.bold}`}>
          From volunteers to volunpeers
        </span>
        {/* TABS */}
        <div
          className={styles.peers__contentTabContainer}
          style={{ marginTop: "2rem", gap: "3rem" }}
        >
          {tabs.map((tab) => {
            return (
              <div
                className={`${
                  selectedTab == tab
                    ? styles.peers__contentTabSelected
                    : styles.peers__contentTab
                } ${textStyles.lg1} ${textStyles.bold}`}
                onClick={() => handleTabClick(tab)}
                key={tab}
              >
                {tab}
              </div>
            );
          })}
        </div>
        {/* COMPLETED QUESTS */}
        {selectedTab == "Add Peers" && (
          <div className={styles.peers__addPeersContent}>
            {potentialPeers &&
              potentialPeers.map((potentialPeer) => {
                return (
                  <Peer
                    key={potentialPeer.peerId}
                    peerId={potentialPeer.peerId}
                    name={potentialPeer.name}
                    quests={potentialPeer.quests}
                  ></Peer>
                );
              })}
          </div>
        )}
        {/* YOUR VOLUNPEERS */}
        {selectedTab == "Your VolunPeers" && <Peers></Peers>}
        {/* QUESTS YOUR PEERS ARE ATTENDING */}
        {selectedTab == "Quests Your Peers Are Attending" && peerQuests && (
          <Cards quests={peerQuests}></Cards>
        )}
      </div>
    </div>
  );
}

export default PeersPage;
