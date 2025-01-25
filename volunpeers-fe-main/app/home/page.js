"use client";

import React, { useState } from "react";
import Navbar from "../components/Navbar";
import styles from "./page.module.scss";
import flexStyles from "@/styles/flex/flex.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import buttonStyles from "@/styles/button/button.module.scss";
import Modal from "../components/modal/Modal";
import useModal from "@/utils/hooks/useModal";
import Login from "../components/modal/Login";
import dynamic from "next/dynamic";

export default function Home() {
  const SignUp = dynamic(() => import("../components/modal/SignUp"));
  const { openModal, toggleModal } = useModal();
  const [selectedLink, setSelectedLink] = useState();

  const handleSelectedLink = (link) => {
    toggleModal();
    setSelectedLink(link);
  };

  return (
    <div className={styles.home}>
      {openModal && selectedLink == "signup" && (
        <Modal>
          <SignUp toggleModal={toggleModal}></SignUp>
        </Modal>
      )}
      {openModal && selectedLink == "login" && (
        <Modal toggleModal={toggleModal}>
          <Login toggleModal={toggleModal}></Login>
        </Modal>
      )}
      <Navbar toggleModal={toggleModal}>
        <div className={flexStyles.flexCenter}>
          <div
            className={`${textStyles.semibold} ${styles.home__login}`}
            onClick={() => handleSelectedLink("login")}
          >
            Log in
          </div>
          <button
            className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
            style={{ marginLeft: "2rem" }}
            onClick={() => handleSelectedLink("signup")}
          >
            Sign up
          </button>
        </div>
      </Navbar>
      <div className={styles.home__contentContainer}>
        {/* INTRO */}
        <div
          className={`${flexStyles.flexCenter} ${styles.home__introContainer}`}
        >
          {/* INTRO LEFT */}
          <div className={styles.home__introLeftContainer}>
            <span className={`${textStyles.xxbold} ${textStyles.xxl}`}>
              The people platform where
            </span>
            <span
              className={`${textStyles.xxbold} ${textStyles.xxl}`}
              style={{ marginTop: "0.5rem" }}
            >
              Volunteers become peers
            </span>
            <span
              className={`${textStyles.md}`}
              style={{
                marginTop: "1.6rem",
                lineHeight: "1.8rem",
              }}
            >
              Volunpeers empowers every individual to make a meaningful impact
              while building strong, reciprocal relationships. Embark on a quest
              today where every act of service is an opportunity for mutual
              growth and community building.
            </span>
            <button
              className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
              style={{ marginTop: "3rem" }}
              onClick={() => handleSelectedLink("signup")}
            >
              Embark on a quest
            </button>
          </div>
          {/* INTRO RIGHT */}
          <div className={styles.home__introRightContainer}>
            <div className={`${styles.home__logo}`}></div>
          </div>
        </div>
      </div>
    </div>
  );
}
