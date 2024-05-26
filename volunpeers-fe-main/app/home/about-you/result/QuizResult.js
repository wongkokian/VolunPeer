"use client";

import React, { useEffect, useState, Fragment } from "react";
import styles from "/app/home/page.module.scss";
import characterStyles from "@/styles/characters/characters.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import buttonStyles from "@/styles/button/button.module.scss";
import { useRouter } from "next/navigation";
import { useRecoilState } from "recoil";
import { personalityState } from "@/recoil/personality/atom";
import PERSONALITY_DESC from "../../../../public/personalityDesc";
function QuizResult() {
  // USE RECOIL HERE TO CONVERT TO INTP OR SOMETHING
  const [personalityRecoil, setPersonalityStateRecoil] =
    useRecoilState(personalityState);
  const [personality, setPersonality] = useState("");

  const { push } = useRouter();
  // Route to dashboard
  const handleClick = () => {
    push("/user/dashboard");
  };

  useEffect(() => {
    // Reset personality
    let personalityString = "";
    for (const [key, value] of Object.entries(personalityRecoil)) {
      personalityString += value;
    }
    setPersonality(personalityString);
  }, [personalityRecoil]);
  // useEffect(() => {
  //   console.log(personality);
  // }, [personality]);

  return (
    <Fragment>
      {personality != "" && personality.length == 4 && (
        <div className={styles.home__result}>
          <span className={`${textStyles.semibold} ${textStyles.xl1}`}>
            Congratulations!!!
          </span>
          <div
            className={`${characterStyles.character} ${
              PERSONALITY_DESC[personality]["src"] === "archer"
                ? characterStyles.character__archer
                : PERSONALITY_DESC[personality]["src"] === "musketeer"
                ? characterStyles.character__musketeer
                : PERSONALITY_DESC[personality]["src"] === "swordsman"
                ? characterStyles.character__swordsman
                : PERSONALITY_DESC[personality]["src"] === "wizard"
                ? characterStyles.character__wizard
                : PERSONALITY_DESC[personality]["src"] === "enchantress"
                ? characterStyles.character__enchantress
                : characterStyles.character__knight
            } ${styles.home__character} `}
          ></div>
          <span
            className={`${textStyles.semibold} ${textStyles.xl}`}
            style={{ marginTop: "1.6rem" }}
          >
            You are a {PERSONALITY_DESC[personality]["role"]}
          </span>
          <span
            className={`${textStyles.md}`}
            style={{
              whiteSpace: "pre-line",
              textAlign: "left",
              marginTop: "1.5rem",
            }}
          >
            {PERSONALITY_DESC[personality]["description"]}
          </span>
          <button
            className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
            style={{ marginTop: "3rem" }}
            onClick={handleClick}
          >
            Embark on a quest
          </button>
        </div>
      )}
    </Fragment>
  );
}

export default QuizResult;
