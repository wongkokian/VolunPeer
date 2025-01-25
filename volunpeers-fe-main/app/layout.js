import "./global.scss";

import { Inter } from "next/font/google";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "VOLUNPEERS",
  description: "Embark on a quest with your volunpeers today!",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <head>
        <link
          rel="icon"
          type="image/png"
          sizes="32x32"
          href="/images/idle/archer_idle.png"
        />
      </head>
      <body className={inter.className}>{children}</body>
    </html>
  );
}
