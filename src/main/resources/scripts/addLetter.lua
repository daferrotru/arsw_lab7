-- addLetter.lua
local value= redis.call('HGET', KEYS[1],"completeWord")
local guessedWord = redis.call('HGET', KEYS[1] ,"guessedWord")
local newGuessed=""

for i=1, #value do
if value:sub(i,i) == ARGV[1]
then 
    newGuessed=newGuessed..ARGV[1]
else
    newGuessed=newGuessed..guessedWord:sub(i,i)
end
end

redis.call('HSET', KEYS[1], "guessedWord" , newGuessed)

return newGuessed