import React from "react";
import styles from "../../styles/navbar/navbar.module.scss";
import buttonStyles from "../../styles/button/button.module.scss";
import flexStyles from "../../styles/flex/flex.module.scss";
import textStyles from "../../styles/text/text.module.scss";

function Navbar({ children }) {
  return (
    <div className={styles.navbar}>
      <div className={flexStyles.flexCenter}>
        <div
          className={`${styles.navbar__walk} ${styles.navbar__walkEnchantress}`}
        ></div>
        <div
          className={`${styles.navbar__walk} ${styles.navbar__walkSwordsman}`}
        ></div>
        <div
          className={`${styles.navbar__walk} ${styles.navbar__walkMusketeer}`}
        ></div>
        <span
          className={`${textStyles.semibold} ${textStyles.lg}`}
          style={{ marginLeft: "1rem", letterSpacing: "3px" }}
        >
          VOLUNPEERS
        </span>
      </div>
      {children}
    </div>
  );
}

export default Navbar;
