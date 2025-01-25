import React, { Fragment, useEffect } from "react";
import { useRecoilState } from "recoil";
import { personalityState } from "@/recoil/personality/atom";
import { interestsState } from "@/recoil/interests/atom";
import { useRouter } from "next/navigation";
import { ADD_PERSONALITY } from "@/utils/constants/path";
import quizStyles from "@/styles/quiz/quiz.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import QuizOptions from "./QuizOptions";
import buttonStyles from "@/styles/button/button.module.scss";

function QuizQuestion({ quizStage, step, increaseStep }) {
  const [personality, setPersonality] = useRecoilState(personalityState);
  const [interests, setInterests] = useRecoilState(interestsState);
  const { push } = useRouter();
  const isButtonDisabled = () => {
    if (quizStage.letter && personality[quizStage.letter] == "") {
      return true;
    }
    return false;
  };
  async function handleNextStep() {
    if (step != 6) {
      increaseStep();
    } else {
      // Send request to backend with collated interests and personality
      let personalityString = "";
      for (const [key, value] of Object.entries(personality)) {
        personalityString += value;
      }
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}${ADD_PERSONALITY}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            token: sessionStorage.getItem("token"),
          },
          body: JSON.stringify({
            personality: personalityString,
            interests: interests,
          }),
        }
      );
      const data = await response.json();
      if (data.returnCode == 200) {
        push("/home/about-you/result");
      }
    }
  }
  return (
    <div className={quizStyles.question}>
      {/* IMAGE CONDITIONAL RENDERING */}
      {step == 1 && (
        <div
          className={`${quizStyles.question__image} ${quizStyles.stage1Image}`}
        ></div>
      )}
      {step == 2 && (
        <div
          className={`${quizStyles.question__image} ${quizStyles.stage2Image}`}
        ></div>
      )}
      {step == 3 && (
        <div
          className={`${quizStyles.question__image} ${quizStyles.stage3Image}`}
        ></div>
      )}
      {step == 4 && (
        <div
          className={`${quizStyles.question__image} ${quizStyles.stage4Image}`}
        ></div>
      )}
      {step == 5 && (
        <div
          className={`${quizStyles.question__image} ${quizStyles.stage5Image}`}
        ></div>
      )}
      {step == 6 && (
        <div
          className={`${quizStyles.question__image} ${quizStyles.stage6Image}`}
        ></div>
      )}
      {/* QUIZ CONTENT */}
      <div className={quizStyles.question__contentContainer}>
        {/* CONTEXT */}
        {quizStage.context && (
          <span className={`${textStyles.lg}`} style={{ marginBottom: "2rem" }}>
            {quizStage.context}
          </span>
        )}
        {/* QUESTION */}
        {quizStage.question && (
          <span className={`${textStyles.lg} ${textStyles.bold}`}>
            {quizStage.question}
          </span>
        )}
        {/* OPTIONS */}
        {quizStage.option_1 && quizStage.option_2 && (
          <QuizOptions
            option1Text={quizStage.option_1.text}
            option1Trait={quizStage.option_1.trait}
            option2Text={quizStage.option_2.text}
            option2Trait={quizStage.option_2.trait}
            letterPosition={quizStage.letter}
          ></QuizOptions>
        )}
        <button
          className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
          style={{ marginTop: "3rem", width: "200px" }}
          onClick={handleNextStep}
          disabled={isButtonDisabled()}
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default QuizQuestion;
