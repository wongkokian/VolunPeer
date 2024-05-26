import React, { Fragment, useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { personalityState } from "@/recoil/personality/atom";
import quizStyles from "@/styles/quiz/quiz.module.scss";

function QuizOption({
  option1Text,
  option1Trait,
  option2Text,
  option2Trait,
  letterPosition,
}) {
  const [selectedOption, setSelectedOption] = useState();
  const [personality, setPersonality] = useRecoilState(personalityState);
  const quizSelectHandler = (optionSelected, optionTrait) => {
    setSelectedOption(optionSelected);
    setPersonality({ ...personality, [letterPosition]: optionTrait });
  };
  // Reset selected option when prop change
  useEffect(() => {
    setSelectedOption("");
  }, [letterPosition]);
  return (
    <Fragment>
      <div
        className={`${quizStyles.option} ${
          selectedOption == "option1" ? quizStyles.option__selected : null
        }`}
        onClick={() => quizSelectHandler("option1", option1Trait)}
      >
        {option1Text}
      </div>
      <div
        className={`${quizStyles.option} ${
          selectedOption == "option2" ? quizStyles.option__selected : null
        }`}
        onClick={() => quizSelectHandler("option2", option2Trait)}
      >
        {option2Text}
      </div>
    </Fragment>
  );
}

export default QuizOption;
