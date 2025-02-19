import { motion } from "framer-motion";
import sample1 from "../../assets/images/sample.png";
import like from "../../assets/images/like.svg";
import sample2 from "../../assets/images/sample2.png";
import pie from "../../assets/images/pie.svg";
import robot from "../../assets/images/robot.png";
import chat from "../../assets/images/chat.png";
import search from "../../assets/images/search.png";

const planets = [
  { size: 80, x: "38vw", y: "70vh", color: "from-purple-500 to-blue-500" },
  { size: 50, x: "30vw", y: "10vh", color: "from-pink-500 to-purple-500" },
  { size: 360, x: "80vw", y: "80vh", color: "from-blue-500 to-cyan-500" },
  { size: 100, x: "20vw", y: "30vh", color: "from-purple-900 to-blue-900" },
  { size: 80, x: "40vw", y: "20vh", color: "from-purple-500 to-blue-500" },
  { size: 190, x: "90vw", y: "-5vh", color: "from-pink-500 to-purple-500" },
  { size: 120, x: "70vw", y: "40vh", color: "from-blue-500 to-cyan-500" },
  { size: 40, x: "80vw", y: "50vh", color: "from-purple-900 to-blue-900" },
];

export function Landing() {
  return (
    <>
      {/* SECTION 1 */}
      <section className="relative w-full h-screen bg-gradient-to-b from-black to-gray-900 overflow-hidden flex items-center justify-start snap-always snap-center ">
        {planets.map((planet, index) => (
          <motion.div
            key={index}
            initial={{ opacity: 0, scale: 0.5 }}
            whileInView={{ opacity: 1, scale: 1 }}
            transition={{ duration: 1, delay: index * 0.1 }}
            viewport={{ once: true }}
            className={`absolute bg-gradient-to-br ${planet.color} rounded-full`}
            style={{
              width: planet.size,
              height: planet.size,
              left: planet.x,
              top: planet.y,
            }}
          />
        ))}
        {/* 텍스트 */}
        <motion.div
          className="text-white text-5xl font-semibold text-center px-4"
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          transition={{ duration: 1 }}
          viewport={{ once: true }}
        >
          <div className="flex flex-col justify-start text-start gap-2 ms-40">
            <span>코드리뷰의 모든 것,</span>
            <span>깃잇에서 쉽고 간편하게</span>
          </div>
        </motion.div>
      </section>
      <div className="bg-gradient-to-b from-white to-[#D7EFFE]">
        {/* SECTION 2 */}
        <section className="relative w-full h-screen from-black to-gray-900 overflow-hidden flex items-center justify-start p-[100px] snap-always snap-center scroll-smooth">
          <motion.div
            className="text-5xl font-semibold text-center px-4 flex justify-between items-center w-full"
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 1 }}
            viewport={{ once: true }}
          >
            <div className="flex flex-col justify-start text-start gap-2">
              <span>이젠,</span>
              <span>코드리뷰도</span>
              <span>
                똑똑하고 <span className="text-blue-500">효율적</span>으로
              </span>
            </div>
            <motion.div
              className="w-1/2 relative "
              initial={{ opacity: 0, y: 30 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ duration: 1, delay: 0.2 }}
              viewport={{ once: true }}
            >
              <img
                src={like}
                alt=""
                className="absolute right-[-30px] top-[-30px]"
              />
              <img src={sample1} alt="serviceImage" className="shadow-xl" />
            </motion.div>
          </motion.div>
        </section>

        {/* SECTION 3 */}
        <section className="relative w-full h-screen from-black to-gray-900 overflow-hidden flex items-center justify-start p-[100px] snap-always snap-center scroll-smooth">
          <motion.div
            className="text-5xl font-semibold text-center px-4 flex justify-between items-center w-full"
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 1 }}
            viewport={{ once: true }}
          >
            <div className="flex flex-col justify-start text-start gap-2">
              <span>한눈에 보는</span>
              <span>팀 프로젝트의</span>
              <span>
                <span className="text-blue-500">성능</span>과{" "}
                <span className="text-blue-500">현황</span>까지
              </span>
            </div>
            <motion.div
              className="w-1/2 relative "
              initial={{ opacity: 0, y: 30 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ duration: 1, delay: 0.2 }}
              viewport={{ once: true }}
            >
              <img
                src={pie}
                alt="pie"
                className="absolute right-[-30px] top-[-50px]"
              />
              <img src={sample2} alt="servieImage" />
            </motion.div>
          </motion.div>
        </section>
        <section className="relative w-full h-screen from-black to-gray-900 overflow-hidden flex items-center justify-start p-[100px] snap-always snap-center scroll-smooth">
          <div className="bg-[#EFF8FD] w-[80%] m-auto flex flex-col h-[600px] gap-10 rounded-xl justify-center items-center">
            <div className="flex w-full justify-around">
              <div className="flex flex-col gap-2">
                <img
                  src={robot}
                  alt="robot"
                  className="w-[100px] h-[100px] object-cover"
                />
                <span className="text-xl font-bold">스마트한 AI 코드리뷰</span>
                <div className="flex flex-col text-lg">
                  <span>MR이 업로드 되면, AI가 먼저 리뷰를 진행해드려요.</span>
                  <span>코드리뷰가 어려울 땐, AI 리뷰를 먼저 읽어보세요.</span>
                </div>
              </div>
              <div className="flex flex-col gap-2">
                <img
                  src={chat}
                  alt="chat"
                  className="w-[100px] h-[100px] object-cover"
                />
                <span className="text-xl font-bold">WebHook으로 알림까지</span>
                <div className="flex flex-col text-lg">
                  <span>
                    MatterMost와 연동하여 팀 채널에 알림을 보내드려요.
                  </span>
                  <span>MR, 댓글까지 어느 하나 놓치지 마세요!</span>
                </div>
              </div>
            </div>
            <div className="flex w-full justify-around">
              <div className="flex flex-col gap-2">
                <img
                  src={search}
                  alt="robot"
                  className="w-[100px] h-[100px] object-cover"
                />
                <span className="text-xl font-bold">프로젝트 대시보드</span>
                <div className="flex flex-col text-lg">
                  <span>우리 팀 프로젝트의 현황이 궁금하다면 ?</span>
                  <span>MR, Commit, Contributor 현황을 확인해보세요.</span>
                </div>
              </div>
              <div className="flex flex-col gap-2">
                <img
                  src={pie}
                  alt="robot"
                  className="w-[100px] h-[100px] object-cover"
                />
                <span className="text-xl font-bold">Light House 성능 측정</span>
                <div className="flex flex-col text-lg">
                  <span>우리 프로젝트의 Front 성능 점수가 궁금하다면 ?</span>
                  <span>성능측정 점수를 통해 프로젝트를 개선해보세요 !</span>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </>
  );
}
