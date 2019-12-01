let userScore = 0;
let computerScore = 0;
const userScore_span = document.getElementById('user-score');
const computerScore_span = document.getElementById('computer-score');
const scoreBoard_div = document.querySelector('.score-board');
const result_p = document.querySelector('.result > p');
const racoon_div = document.getElementById('r');
const platypus_div = document.getElementById('p');
const snake_div = document.getElementById('s');


function convertToWord(letter){
  if(letter ==="r")return "Racoon";
  if(letter ==="p")return "Platypus";
  if(letter ==="s")return "Snake";
}

function getComputerChoice(){
  const choices = ['r','p','s'];
  const randomNumber = Math.floor(Math.random() * 3);
  return choices[randomNumber];
}

function draw(userChoice,computerChoice){
  const smallUserWord = "user".fontsize(1).sub();
  const smallCompWord = "comp".fontsize(1).sub();
  result_p.innerHTML = convertToWord(userChoice) + smallUserWord +  "   and " + convertToWord(computerChoice)+ smallCompWord + "    became friends! Its a draw!🤗";
}
function win(userChoice,computerChoice){
  const smallUserWord = "user".fontsize(1).sub();
  const smallCompWord = "comp".fontsize(1).sub();
  userScore++;
  userScore_span.innerHTML = userScore;

  if(userChoice === "r")
  result_p.innerHTML = convertToWord(userChoice) + smallUserWord + " scratches " + convertToWord(computerChoice) + smallCompWord + " You win!🔥";
  else if (userChoice === "p")
  result_p.innerHTML = convertToWord(userChoice) + smallUserWord + " slaps " + convertToWord(computerChoice) + smallCompWord + " You win!🔥";
  else if (userChoice === "s")
  result_p.innerHTML = convertToWord(userChoice) + smallUserWord + "  devours " + convertToWord(computerChoice) + smallCompWord + " You win!🔥";
}
function lose(userChoice,computerChoice){
  const smallUserWord = "user".fontsize(1).sub();
  const smallCompWord = "comp".fontsize(1).sub();
  computerScore++;
  computerScore_span.innerHTML = computerScore;
  if(computerChoice === "r")
  result_p.innerHTML = convertToWord(computerChoice) +smallCompWord + " scratches " + convertToWord(userChoice) + smallUserWord + " You Lose!😥";
  else if (computerChoice==="p")
  result_p.innerHTML = convertToWord(computerChoice) +smallCompWord + " slaps " + convertToWord(userChoice) + smallUserWord + " You Lose!😥";
  else if (computerChoice==="s")
  result_p.innerHTML = convertToWord(computerChoice) +smallCompWord + " devours " + convertToWord(userChoice) + smallUserWord + " You Lose!😥";
}


function game(userChoice){
  const computerChoice = getComputerChoice();

  switch(userChoice + computerChoice){
    case "rr":
    case "ss":
    case "pp":
      draw(userChoice,computerChoice);
      break;
    case "rs":
    case "sp":
    case "pr":
      win(userChoice,computerChoice);
      break;
    case "sr":
    case "ps":
    case "rp":
      lose(userChoice,computerChoice);
      break;
  }
}

function main(){
racoon_div.addEventListener('click', () => game("r"));
platypus_div.addEventListener('click', () => game("p"));
snake_div.addEventListener('click', () => game("s"));
}

main();
