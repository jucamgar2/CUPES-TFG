const gridContainer = document.querySelector(".grid-container");
let cards = [];
let fc, sc;
let lockBoard = false;
let shifts = 0;

document.querySelector(".shifts").textContent = shifts;

fetch("/js/cards.json")
  .then((res) => res.json())
  .then((data) => {
    const randomIndexes = getRandomIndexes(data, 9);
    cards = randomIndexes.map(index => data[index]);
    cards = [...cards, ...cards];
    shuffleCards();
    generateCards();
  });

function getRandomIndexes(data, count) {
  const indexes = [];
  while (indexes.length < count) {
    const randomIndex = Math.floor(Math.random() * data.length);
    if (!indexes.includes(randomIndex)) {
      indexes.push(randomIndex);
    }
  }
  return indexes;
}

function shuffleCards() {
  let currentIndex = cards.length,
    randomIndex,
    temporaryValue;
  while (currentIndex !== 0) {
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;
    temporaryValue = cards[currentIndex];
    cards[currentIndex] = cards[randomIndex];
    cards[randomIndex] = temporaryValue;
  }
}

function generateCards() {
  for (let card of cards) {
    const cardElement = document.createElement("div");
    cardElement.classList.add("card");
    cardElement.setAttribute("data-name", card.name);
    cardElement.innerHTML = `
      <div class="front">
        <img class="front-image" src=${card.image} />
      </div>
      <div class="back"></div>
    `;
    gridContainer.appendChild(cardElement);
    cardElement.addEventListener("click", flipCard);
  }
}

function flipCard() {
  if (lockBoard) return;
  if (this === fc) return;

  this.classList.add("flipped");

  if (!fc) {
    fc = this;
    return;
  }

  sc = this;
  shifts++;
  document.querySelector(".shifts").textContent = shifts;
  lockBoard = true;

  checkForMatch();
}

function checkForMatch() {
  let isMatch = fc.dataset.name === sc.dataset.name;

  isMatch ? disableCards() : unflipCards();
}

function disableCards() {
  fc.removeEventListener("click", flipCard);
  sc.removeEventListener("click", flipCard);

  resetBoard();
}

function unflipCards() {
  setTimeout(() => {
    fc.classList.remove("flipped");
    sc.classList.remove("flipped");
    resetBoard();
  }, 1000);
}

function resetBoard() {
  fc = null;
  sc = null;
  lockBoard = false;
}

function restart() {
  resetBoard();
  shuffleCards();
  shifts = 0;
  document.querySelector(".shifts").textContent = shifts;
  gridContainer.innerHTML = "";
  generateCards();
}