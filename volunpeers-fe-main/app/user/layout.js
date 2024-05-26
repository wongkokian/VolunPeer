"use client";

import React, { Fragment } from "react";
import Navbar from "../components/Navbar";
import navStyles from "@/styles/navbar/navbar.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import flexStyles from "@/styles/flex/flex.module.scss";
import buttonStyles from "@/styles/button/button.module.scss";
import { ConnectionsIcon } from "@/app/components/svg/ConnectionsIcon";
import { ExploreIcon } from "@/app/components/svg/ExporeIcon";
import { useRouter } from "next/navigation";
import Link from "next/link";

function UserLayout({ children }) {
  const { push } = useRouter();
  const navLinks = [
    {
      href: `/user/dashboard`,
      text: "Home",
    },
    {
      href: `/user/explore`,
      text: "Explore",
    },
    {
      href: `/user/peers`,
      text: "Peers",
    },
    {
      href: `/user/profile`,
      text: "Profile",
    },
  ];
  const handleLogOut = () => {
    sessionStorage.clear();
    push(`/home`);
  };
  return (
    <Fragment>
      <Navbar>
        <div className={flexStyles.flexCenter} style={{ gap: "3rem" }}>
          {navLinks.map((navLink) => {
            return (
              <Link
                key={navLink}
                href={navLink.href}
                style={{ textDecoration: "none", color: "black" }}
              >
                <div className={`${navStyles.navbar__iconContainer}`}>
                  {navLink.text == "Home" && (
                    <div
                      className={`${navStyles.navbar__icon} ${navStyles.navbar__iconHome}`}
                    ></div>
                  )}
                  {navLink.text == "Explore" && (
                    <div className={`${navStyles.navbar__icon}`}>
                      <ExploreIcon color="black" />
                    </div>
                  )}
                  {navLink.text == "Profile" && (
                    <div className={`${navStyles.navbar__icon}`}>
                      <ConnectionsIcon color="black" />
                    </div>
                  )}
                  {navLink.text == "Peers" && (
                    <div className={`${navStyles.navbar__icon}`}>
                      <ConnectionsIcon color="black" />
                    </div>
                  )}
                  <span className={`${textStyles.sm} ${textStyles.bold}`}>
                    {navLink.text}
                  </span>
                </div>
              </Link>
            );
          })}
          <button
            className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
            style={{ paddingTop: "0.5rem", paddingBottom: "0.5rem" }}
            onClick={() => handleLogOut()}
          >
            Log out
          </button>
        </div>
      </Navbar>
      <div
        style={{
          overflowX: "hidden",
          backgroundColor: "rgb(248 250 252)",
          height: "100dvh",
        }}
      >
        {children}
      </div>
    </Fragment>
  );
}

export default UserLayout;
