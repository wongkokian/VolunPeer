import { useState } from "react";

const useAboutYouStep = () => {
  const [step, setStep] = useState(1);
  const increaseStep = () => {
    setStep((step) => step + 1);
  };

  return { step, increaseStep };
};

export default useAboutYouStep;
