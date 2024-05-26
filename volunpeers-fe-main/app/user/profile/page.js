"use client";
import React, { Fragment, useEffect, useState } from "react";
import textStyles from "@/styles/text/text.module.scss";
import Cards from "../../components/cards/Cards";
import Card from "@/app/components/cards/Card";
import styles from "./page.module.scss";
import characterStyles from "@/styles/characters/characters.module.scss";
import badgeStyles from "@/styles/badges/badges.module.scss";
import Select from "react-select";
import { PROFILE } from "@/utils/constants/path";
import { GET_UPCOMING_QUESTS } from "@/utils/constants/path";

import PERSONALITY_DESC from "../../../public/personalityDesc";

function Profile() {
  const imageUrl =
    "https://img.fixthephoto.com/blog/images/gallery/news_preview_mob_image__preview_11368.png";
  const badges = [5, 5, 5];

  const [peer, setPeer] = useState({
    name: "",
    connections: [],
    interests: [],
    locationName: "",
    personality: "INTP",
  });
  const [quests, setQuests] = useState([]);

  useEffect(() => {
    async function getPeer() {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}${PROFILE}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            token: sessionStorage.getItem("token"),
          },
        }
      );
      const peerData = await response.json();
      console.log(peerData);
      setPeer(peerData);
    }
    getPeer();
  }, []);

  useEffect(() => {
    async function getUpcoming() {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}${GET_UPCOMING_QUESTS}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            token: sessionStorage.getItem("token"),
          },
        }
      );
      const questData = await response.json();
      console.log(questData.upcomingQuests);
      setQuests(questData.upcomingQuests);
    }
    getUpcoming();
  }, []);
  return (
    <Fragment>
      <div
        className={styles.profile__containerLeftRight}
        style={{ paddingBottom: "4rem" }}
      >
        <div className={styles.profile__leftContentContainer}>
          <div
            className={styles.profile__image}
            style={{ backgroundImage: `url(${imageUrl})` }}
          />
          <div className={styles.profile__name}>
            <span className={`${textStyles.xxl} ${textStyles.bold}`}>
              {peer.name}
            </span>
          </div>
          <div className={`${styles.profile__belowName}`}>
            <span className={`${textStyles.lg1} ${textStyles.bold}`}>
              {PERSONALITY_DESC[peer.personality]["role"]}
            </span>
            <span
              className={`${textStyles.lg1} ${textStyles.bold}`}
              style={{ marginTop: "3px" }}
            >
              {peer.locationName}
            </span>
            <span
              className={`${textStyles.lg1} ${textStyles.bold}`}
              style={{ marginTop: "3px" }}
            >
              {peer.connections.length} peers
            </span>
            <div
              className={styles.profile__interestsContainer}
              style={{ marginTop: "5px" }}
            >
              {peer.interests.map((element) => (
                <div
                  key={element}
                  className={`${styles.profile__interests} ${textStyles.lg1} ${textStyles.bold}`}
                >
                  {element}
                </div>
              ))}
            </div>
            <div
              className={`${characterStyles.character} ${
                PERSONALITY_DESC[peer.personality]["src"] === "archer"
                  ? characterStyles.character__archer
                  : PERSONALITY_DESC[peer.personality]["src"] === "musketeer"
                  ? characterStyles.character__musketeer
                  : PERSONALITY_DESC[peer.personality]["src"] === "swordsman"
                  ? characterStyles.character__swordsman
                  : PERSONALITY_DESC[peer.personality]["src"] === "wizard"
                  ? characterStyles.character__wizard
                  : PERSONALITY_DESC[peer.personality]["src"] === "enchantress"
                  ? characterStyles.character__enchantress
                  : characterStyles.character__knight
              } ${styles.profile__character} `}
              style={{ marginTop: "20px" }}
            ></div>
          </div>
        </div>
        <div className={styles.profile__rightContentContainer}>
          <span className={`${textStyles.lg1} ${textStyles.bold}`}>
            Upcoming Events
          </span>
          <div style={{ display: "flex", width: "1200px" }}>
            {/* {quests.map((questi) => (
              <Card
                key={questi.questId || Math.random()}
                title={questi.title}
                orgName={questi.orgName}
                startDateTime={questi.startDateTime}
                endDateTime={questi.endDateTime}
                imageUrl={questi.imageUrl}
                numberGoing={questi.numRegistered}
              />
            ))} */}
            <Cards type="upcoming" quests={quests}></Cards>
          </div>
          <div style={{ marginTop: "2rem" }}>
            <span className={`${textStyles.lg1} ${textStyles.bold}`}>
              Badges
            </span>
            <div
              className={styles.profile__badgesContainer}
              style={{ marginTop: "1.5rem" }}
            >
              {/* {badges.map((badge) => (
                <div
                  key={badge.id || Math.random()}
                  className={`${badgeStyles.badge} ${badgeStyles.badge__animal} ${styles.profile__badge}`}
                />
              ))} */}
              <div
                className={`${badgeStyles.badge} ${badgeStyles.badge__animal} ${styles.profile__badge}`}
              />
              <div
                className={`${badgeStyles.badge} ${badgeStyles.badge__eldercare} ${styles.profile__badge}`}
              />
              <div
                className={`${badgeStyles.badge} ${badgeStyles.badge__environment} ${styles.profile__badge}`}
              />
            </div>
          </div>
        </div>
      </div>
    </Fragment>
  );
}

export default Profile;
