//When coloring a striped pattern, you may start by coloring each square sequentially, meaning you spend time needing to switch coloring pencils.
//Create a function where given an array of colors cols, return how long it takes to color the whole pattern. Note the following times:
//
//    It takes 1 second to switch between pencils.
//    It takes 2 seconds to color a square.
//
//See the example below for clarification.
//<code>color_pattern_times(["Red", "Blue", "Red", "Blue", "Red"]) ➞ 14</code>
//<code>// There are 5 colors so it takes 2 seconds to color each one (2 x 5 = 10).</code>
//<code>// You need to switch the pencils 4 times and it takes 1 second to switch (1 x 4 = 4).</code>
//<code>// 10 + 4 = 14</code>
//Examples
//<code>colorPatternTimes(["Blue"]) ➞ 2</code>
//<code>colorPatternTimes(["Red", "Yellow", "Green", "Blue"]) ➞ 11</code>
//<code>colorPatternTimes(["Blue", "Blue", "Blue", "Red", "Red", "Red"]) ➞ 13</code>
//Notes
//
//    Only change coloring pencils if the next color is different to the color before it.
//    Return a number in seconds.
function colorPatternTimes(cols) {
    let timeTotal = 0;  // seconds taken to color and change pens
    for(let i=0; i<cols.length; i++) {        
        timeTotal = timeTotal + 2;
        if (i>0 && cols[i-1] != cols[i]) {
            timeTotal = timeTotal + 1;
        }
    }
    return timeTotal;
}

// To run from from bash:
// node question1Solution.js
console.log(colorPatternTimes(["Red", "Blue", "Red", "Blue", "Red"]));
console.log(colorPatternTimes(["Blue"]));
console.log(colorPatternTimes(["Red", "Yellow", "Green", "Blue"]));
console.log(colorPatternTimes(["Blue", "Blue", "Blue", "Red", "Red", "Red"]));