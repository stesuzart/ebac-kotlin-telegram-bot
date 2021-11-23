package bots

import com.vdurmont.emoji.EmojiParser
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class KotlinBot : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        //return bot username
        // If bot username is @HelloKotlinBot, it must return
        return "HelloKotlinBot"
    }

    override fun getBotToken(): String {
        // Return bot token from BotFather
        return "[TOKEN-TELEGRAM]"
    }

    override fun onUpdateReceived(update: Update?) {
        // We check if the update has a message and the message has text
        val nameSender = update?.message?.from?.firstName
        val chatId = update?.message?.chatId.toString()
        val messageCommand = update?.message?.text

        try {
            if(messageCommand=="/start") {
                val sendDocument = SendDocument().apply {
                    this.chatId = chatId
                    this.caption = EmojiParser.parseToUnicode("Oláaa $nameSender :smile:")
                    this.document = InputFile().setMedia("https://media.giphy.com/media/SKGo6OYe24EBG/giphy.gif")
                    this.parseMode = "MarkdownV2"
                }

                execute(sendDocument)
            } else {
                val sendDocument = SendMessage().apply {
                    this.chatId = chatId
                    this.text = EmojiParser.parseToUnicode("não funcionou :disappointed:")
                    this.parseMode = "MarkdownV2"
                }

                execute(sendDocument)
            }
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}