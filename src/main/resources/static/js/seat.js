
function setMax(obj, r1, r2) {
    let adult;
    let child;
    if(obj.value == 1)
    {
        child = document.getElementById('Child');
        child.setAttribute("max", r1)
        adult = document.getElementById('Adult');
        adult.setAttribute("max", r1)
    }
    else
    {
        child = document.getElementById('Child');
        child.setAttribute("max", r2)
        adult = document.getElementById('Adult');
        adult.setAttribute("max", r2)
    }
}

function changeMaxAdult(r1, r2)
{
    let adult = document.getElementById('Adult')
    let child = document.getElementById('Child')
    let oldValue = adult.defaultValue
    let newValue = adult.value
    if(document.getElementById('class1').checked)
        ruleMax = r1
    else
        ruleMax = r2
    if(oldValue < newValue)
        newRule = ruleMax -  newValue
    else
        newRule = ruleMax + (oldValue - newValue)
    child.setAttribute("max", newRule)
    adult.defaultValue = newValue
}


function changeMaxChild(r1, r2)
{
    let adult = document.getElementById('Adult')
    let child = document.getElementById('Child')
    let oldValue = child.defaultValue
    let newValue = child.value
    if(document.getElementById('class1').checked)
        ruleMax = r1
    else
        ruleMax = r2
    if(oldValue < newValue)
        newRule = ruleMax -  newValue
    else
        newRule = ruleMax + (oldValue - newValue)
    adult.setAttribute("max", newRule)
    child.defaultValue = newValue
}