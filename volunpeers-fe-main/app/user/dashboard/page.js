"use client";

import React, { Fragment, useState, useEffect } from "react";
import styles from "./page.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import Cards from "../../components/cards/Cards";
import {
  GET_ALL_RECOMMENDED_QUESTS,
  GET_RECOMMENDED_QUESTS_INTEREST,
  GET_RECOMMENDED_QUESTS_PERSONALITY,
  GET_UPCOMING_QUESTS,
} from "@/utils/constants/path";

function Dashboard() {
  const [selectedTab, setSelectedTab] = useState("all");
  const [quests, setQuests] = useState();
  const [upcomingQuests, setUpcomingQuests] = useState();

  const handleTabClick = (tab) => {
    setSelectedTab(tab);
    if (tab == "personality") {
      getQuests(GET_RECOMMENDED_QUESTS_PERSONALITY);
    }
    if (tab == "interests") {
      getQuests(GET_RECOMMENDED_QUESTS_INTEREST);
    }
    if (tab == "all") {
      getQuests(GET_ALL_RECOMMENDED_QUESTS);
    }
  };

  async function getQuests(path) {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}${path}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          token: sessionStorage.getItem("token"),
        },
      }
    );
    const questsData = await response.json();
    setQuests(questsData.recommendations);
  }

  useEffect(() => {
    async function getRecommendedQuests() {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}${GET_ALL_RECOMMENDED_QUESTS}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            token: sessionStorage.getItem("token"),
          },
        }
      );
      const questsData = await response.json();
      setQuests(questsData.recommendations);
    }
    async function getUpcomingQuests() {
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
      const questsData = await response.json();
      setUpcomingQuests(questsData.upcomingQuests);
    }
    getUpcomingQuests();
    getRecommendedQuests();
  }, []);
  return (
    <Fragment>
      <div className={styles.dashboard__contentContainer}>
        {upcomingQuests && (
          <div
            className={styles.dashboard__upcoming}
            style={{ marginBottom: "4rem" }}
          >
            <span className={`${textStyles.xl1} ${textStyles.bold}`}>
              Upcoming quests
            </span>
            <Cards type="upcoming" quests={upcomingQuests}></Cards>
          </div>
        )}
        <div className={`${styles.dashboard__recommended}`}>
          <span className={`${textStyles.xl1} ${textStyles.bold}`}>
            Recommended quests for you
          </span>
          <div
            className={styles.dashboard__recommendedTabContainer}
            style={{ marginTop: "2rem", gap: "3rem" }}
          >
            <div
              className={`${
                selectedTab == "all"
                  ? styles.dashboard__recommendedTabSelected
                  : styles.dashboard__recommendedTab
              } ${textStyles.lg1} ${textStyles.bold}`}
              onClick={() => handleTabClick("all")}
            >
              All
            </div>
            <div
              className={`${
                selectedTab == "personality"
                  ? styles.dashboard__recommendedTabSelected
                  : styles.dashboard__recommendedTab
              } ${textStyles.lg1} ${textStyles.bold}`}
              onClick={() => handleTabClick("personality")}
            >
              Personality
            </div>
            <div
              className={`${
                selectedTab == "interests"
                  ? styles.dashboard__recommendedTabSelected
                  : styles.dashboard__recommendedTab
              } ${textStyles.lg1} ${textStyles.bold}`}
              onClick={() => handleTabClick("interests")}
            >
              Interests
            </div>
          </div>
          {quests && <Cards quests={quests} />}
        </div>
      </div>
    </Fragment>
  );
}

export default Dashboard;
