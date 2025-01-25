"use client";

import React, { Fragment, useEffect, useState } from "react";
import styles from "./page.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import { GET_ALL_QUESTS } from "@/utils/constants/path";
import Cards from "@/app/components/cards/Cards";

function Explore() {
  const [quests, setQuests] = useState();
  useEffect(() => {
    async function getAllQuests() {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}${GET_ALL_QUESTS}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            token: sessionStorage.getItem("token"),
          },
        }
      );
      const questsData = await response.json();
      setQuests(questsData.quests);
    }
    getAllQuests();
  }, []);
  return (
    <div className={styles.explore__container}>
      <div className={styles.explore__content}>
        <span className={`${textStyles.xl1} ${textStyles.bold}`}>
          Embark on a quest today
        </span>
        <Fragment>{quests && <Cards quests={quests}></Cards>}</Fragment>
      </div>
    </div>
  );
}

export default Explore;
