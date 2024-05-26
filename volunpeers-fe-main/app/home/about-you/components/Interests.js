"use client";

import React, { Fragment, useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import { interestsState } from "@/recoil/interests/atom";
import Select from "react-select";
import makeAnimated from "react-select/animated";
import { INTERESTS } from "@/utils/constants/interests";
import { SELECT_STYLES } from "@/styles/select/selectStyles";
import buttonStyles from "@/styles/button/button.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import styles from "../page.module.scss";

function Interests({ increaseStep }) {
  const [interestsRecoil, setInterestsRecoil] = useRecoilState(interestsState);
  const interests = INTERESTS;
  const animatedComponents = makeAnimated();
  const id = Date.now().toString();
  const [isMounted, setIsMounted] = useState(false);
  const [selectedOptions, setSelectedOptions] = useState();
  const handleSelectOptions = (e) => {
    setSelectedOptions(e);
  };
  const handleNextClick = () => {
    let interestsArray = [];
    if (selectedOptions) {
      for (let option of selectedOptions) {
        interestsArray.push(option.value);
      }
      setInterestsRecoil(interestsArray);
    }
    increaseStep();
  };

  useEffect(() => setIsMounted(true), []);

  return (
    <Fragment>
      <span className={`${textStyles.xl} ${textStyles.bold}`}>
        Get started by picking a few interests
      </span>
      <span
        className={`${textStyles.md} ${textStyles.light}`}
        style={{ marginTop: "1.5rem" }}
      >
        These help with future quest and guild recommendations.
      </span>
      <div className={styles.aboutyou__select} style={{ marginTop: "2rem" }}>
        {isMounted && (
          <Select
            id={id}
            closeMenuOnSelect={false}
            components={animatedComponents}
            isMulti
            options={interests}
            styles={SELECT_STYLES}
            onChange={(e) => handleSelectOptions(e)}
          />
        )}
      </div>
      <div className={styles.aboutyou__buttonContainer}>
        <button
          className={`${buttonStyles.buttonRose} ${buttonStyles.button}`}
          style={{ width: "16rem" }}
          onClick={handleNextClick}
        >
          Next
        </button>
      </div>
    </Fragment>
  );
}

export default Interests;
