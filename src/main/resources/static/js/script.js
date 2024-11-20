
let defaultTheme = "dark";
let otherTheme  = defaultTheme === "light" ? 'dark' : 'light';
let themeHandler = document.querySelector('html');
let themeButton = document.querySelector('#themeChangeButton');
let themeButtonValue = document.querySelector('#themeChangeButtonValue');

implementExistingTheme();

function implementExistingTheme(){
    let currentTheme = getThemeFromLocalStorage();
    let newTheme = currentTheme === null || currentTheme === defaultTheme ? defaultTheme : otherTheme;
    let oldTheme = newTheme === 'light' ? 'dark' : 'light';


    if(!themeHandler.classList.contains(newTheme)){
        themeHandler.classList.length>0 ? themeHandler.classList.remove(currentTheme):'';
        themeHandler.classList.add(newTheme);
        themeButtonValue.textContent = oldTheme;
        setThemeInLocalStorage(newTheme);
    }

}

// -------------change theme button-----------------------------

themeButton.addEventListener('click',changeThemeOnClick);

function changeThemeOnClick(){
    let currentTheme = getThemeFromLocalStorage();
    let newTheme = currentTheme === 'light'? 'dark':'light';

    if(!themeHandler.classList.contains(newTheme)){
        themeHandler.classList.remove(currentTheme);
        themeHandler.classList.add(newTheme);
        themeButtonValue.textContent = currentTheme.toLowerCase();
        setThemeInLocalStorage(newTheme);
    }
    
}
    




function setThemeInLocalStorage(themeSelected){
   localStorage.setItem("theme",themeSelected);
   return;
}

function getThemeFromLocalStorage(){
   return localStorage.getItem("theme");
}