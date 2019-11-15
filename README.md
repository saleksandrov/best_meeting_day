# Best Meeting Day

## Описание всех Endpoints

#### /vote/start Создание голосования
`
{
   "startDate": "2019-11-01",
   "endData": "2019-12-10",
   "bestDates": ["2019-11-01", "2019-11-02", "2019-11-03", "2019-12-01"],
   "creator": "Sergey Aleksandrov" 
}
`

Ответ

`
{
   "voteId": 123 
   "status": "OK",
   "message": "Голование создано успешно"
}
`

#### /vote/add Добавление голоса

`
{
   "voteId": 123 
   "bestDates": ["2019-11-01", "2019-11-02", "2019-11-03", "2019-12-01"],
   "author": "Sergey Aleksandrov" 
}
`

Ответ 
`
{
   "voteId": 123 
   "status": "OK",
   "message": "Голос добавлен"
}
`

#### /vote/getBestDates Результат

`
{
   "voteId": 123 
}
`

Ответ 

`
{
   "voteId": 123,
   "bestDay": "2019-11-01",
   "bestDayVoters": ["Dmitry", "Alexey" ... ],
   "bestDayWithCreator": "2019-11-02",
   "bestDayWithCreatorVoters": ["Dmitry", "Alexey" ...]
}
`

#### /vote/showVotesByDate Показать список проголосовавших
(Кандидат на удаление)
`
{
   "voteId": 123 
   "forDate": "2019-11-01"
}
`

Ответ 

`
{
   "voteId": 123,
   "voters": ["Sergey", "Anton", "Dmitry"]  
}
`
