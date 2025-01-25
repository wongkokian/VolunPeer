export const quiz_stages = {
  quiz_stage1: {
    context:
      "You swing the axe with practiced ease, splitting logs for the village fire. As the son of the village head, your days are filled with helping neighbors and tending to village needs. But today, the familiar rhythm of your life is shattered. Your friends burst into the woodcutter's shed, their usual laughter replaced by frantic excitement.",
  },
  quiz_stage2: {
    context:
      "‘There's a new dungeon near Whispering Falls!’ shouts Anya, the nimble rogue, her eyes sparkling with adventure. ‘Rumors say it holds legendary treasures! We're forming a party to explore it. You in?’ The news hangs heavy in the air. Anya's enthusiasm is infectious.",
    question: "How do you react to their sudden announcement?",
    option_1: {
      text: "A jolt of excitement courses through you. The prospect of adventure explodes in your chest. You imagine the thrill of discovery, the camaraderie with your friends, and the chance to prove yourself beyond village duties. The whispers of treasure and the unknown beckon like a siren's song.",
      trait: "E",
    },
    option_2: {
      text: "A knot of worry tightens in your stomach. Leaving the village while people depend on you feels wrong. Yet, the excitement radiating from your friends is infectious. Perhaps there's a way to contribute that keeps you connected to your responsibilities.",
      trait: "I",
    },
    letter: "first",
  },
  quiz_stage3: {
    context:
      "The weight of your responsibility as the village head's son settles on your shoulders. Leaving the villagers while they depend on you feels wrong.",
    question:
      "How do you feel about leaving the village while people depend on you?",
    option_1: {
      text: "Perhaps this expedition could benefit the village. The treasures within could provide for much-needed repairs, new tools, or even establish trade routes with neighboring villages. You could use your leadership skills to ensure the spoils are used wisely.",
      trait: "T",
    },
    option_2: {
      text: "But leaving the villagers feels like a betrayal. They rely on you to settle disputes, organize repairs, and ensure their safety. What if trouble arises while you're gone? The knot of worry tightens in your stomach.",
      trait: "F",
    },
    letter: "third",
  },
  quiz_stage4: {
    context:
      "As the party approaches the imposing entrance of the Whispering Falls dungeon, a cold wind whips through the trees, carrying the faint scent of mildew and decay.",
    question: "What's your primary focus at this moment?",
    option_1: {
      text: "Maybe a detailed plan is best. Gathering information about the dungeon's layout, potential dangers, and even the whispers of forgotten lore from the village elders could be invaluable. A well-prepared team is a successful team, right?",
      trait: "J",
    },
    option_2: {
      text: "Or maybe flexibility is key. Who knows what dangers lurk within? Adapting to the challenges as they arise might be the wiser course. After all, the thrill of adventure lies partly in the unexpected, doesn't it?",
      trait: "P",
    },
    letter: "fourth",
  },
  quiz_stage5: {
    context:
      "‘There it is!’ shouts Anya, the nimble rogue, pointing at a hulking monstrosity. ‘The Guardian of Whispering Falls!’ The battle erupts, a whirlwind of steel and magic. You fight alongside your friends, adrenaline pumping through your veins. Just as you deliver a powerful blow to the beast, something catches your eye",
    question:
      "A strange symbol glows faintly on the creature's chest, pulsing in rhythm with its attacks. What do you do?",
    option_1: {
      text: "A primal instinct tugs at you. There's something about the symbol, a feeling you can't quite place. Perhaps targeting it could be the key to victory. You yell a warning to your friends and charge towards the glowing symbol, ignoring the beast's enraged roar.",
      trait: "N",
    },
    option_2: {
      text: "We had a plan! This sudden change could be a trap. You shout for your friends to hold their positions and regroup. Maybe there's something in the ancient texts the village elder shared that could explain this symbol.",
      trait: "S",
    },
    letter: "second",
  },
  quiz_stage6: {
    context:
      "With a final, coordinated push, your party vanquishes the dungeon's menace. The cheers that erupted upon your return to the village were music to your ears. The recovered treasures brought prosperity, but the most valuable reward was the deepened bond with your friends, forged in the fires of shared adventure. As you settle back into the rhythm of village life, a familiar warmth fills you. You grab your axe, eager to catch up with your neighbors and lend a hand, your journey continues now.",
    finalStage: true,
  },
};
