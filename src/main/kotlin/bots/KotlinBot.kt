package bots

import com.vdurmont.emoji.EmojiParser
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll
import org.telegram.telegrambots.meta.api.methods.send.SendAudio
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.File

class KotlinBot : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        //return bot username
        // If bot username is @HelloKotlinBot, it must return
        return "ebackotlinbot"
    }

    override fun getBotToken(): String {
        // Return bot token from BotFather
        return "TOKEN-API"
    }

    override fun onUpdateReceived(update: Update?) {
        // We check if the update has a message and the message has text
        val nameSender = update?.message?.from?.firstName
        val chatId = update?.message?.chatId.toString()
        val messageCommand = update?.message?.text
        val callBackData = update?.callbackQuery?.data

        try {
            if(messageCommand == "/start") {
                val welcome = EmojiParser.parseToUnicode("""
                    
                    Olá $nameSender, tudo bem? :smile: 
                    
                    *Bora testar os comandos a seguir?*
                    
                    /start \- mensagem inicial
                    /poll \- qual a melhor linguagem?
                    /button \- qual o melhor doctor?
                    /music \- musica para relaxar
                    /text \- mackdown
                """.trimIndent()
                )

                val sendDocument = SendDocument().apply {
                    this.chatId = chatId
                    caption = welcome
                    document = InputFile("https://media.giphy.com/media/bkcbX8SqTCXHG/giphy.gif")
                    parseMode = "MarkdownV2"
                }

                execute(sendDocument)
            }

            if(messageCommand == "/poll"){
                val sendPoll = SendPoll().apply {
                    this.chatId = chatId
                    question = "Qual é a linguagem mais lega?"
                    options = listOf("Java", "Kotlin")
                    type = "quiz"
                    correctOptionId = 1
                    isAnonymous = false
                }

                execute(sendPoll)
            }

            if(messageCommand == "/button"){
                val  buttons = listOf(
                    listOf(
                        InlineKeyboardButton().apply {
                            text = "Matt Smith"
                            callbackData = "/matt"
                        },
                        InlineKeyboardButton().apply {
                            text = "David Tennant"
                            callbackData = "/david"
                        }
                    )
                )

                val options = InlineKeyboardMarkup().apply {
                    keyboard = buttons
                }

                val sendDocument = SendDocument().apply {
                    this.chatId = chatId
                    document = InputFile("https://media.giphy.com/media/l2ZE7SFH6JKz4gRwc/giphy.gif")
                    caption = "Qual foi o melhor Doctor?"
                    replyMarkup = options
                }

                execute(sendDocument)
            }

            if(callBackData == "/david"){
                val callBackChatId = update.callbackQuery?.message?.chatId.toString()

                val sendDocument = SendDocument().apply {
                    this.chatId = callBackChatId
                    caption = "Discordo"
                    document = InputFile("https://media.giphy.com/media/8ggVeQfq7X33y/giphy.gif")
                }

                execute(sendDocument)
            }

            if(callBackData == "/matt"){
                val callBackChatId = update.callbackQuery?.message?.chatId.toString()

                val sendDocument = SendDocument().apply {
                    this.chatId = callBackChatId
                    caption = "Concordo"
                    document = InputFile("https://media.giphy.com/media/nmQTemUrporWo/giphy.gif")
                }
                execute(sendDocument)
            }

            if(messageCommand == "/music"){

                val sendAudio = SendAudio().apply {
                    this.chatId = chatId
                    audio = InputFile(File("C:\\Users\\steph\\Downloads\\ebac-kotlin-telegram-bot\\src\\main\\resources\\gorillaz.mp3"))
                    title = "I Feel Good - Gorillaz"
                    caption = "musiquinha bacanuda"
                }
                execute(sendAudio)
            }

            if(messageCommand == "/text"){
                val markdownV2Text = """
                    UTILIZANDO MARKDOWN:
                    
                    *bold \*text*
                    _italic \*text_
                    __underline__
                    ~strikethrough~
                    *bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
                    [inline URL](http://www.example.com/)
                    [inline mention of a user](tg://user?id=123456789)
                    `inline fixed-width code`
                    ```kotlin
                    fun main() {
                        println("Hello Kotlin!")
                    }
                    ```
                """.trimIndent()

                val sendMessage = SendMessage().apply {
                    this.chatId = chatId
                    text = markdownV2Text
                    parseMode = "MarkDownV2"

                }
                execute(sendMessage)
            }
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}