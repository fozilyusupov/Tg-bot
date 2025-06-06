package com.example.tgbot1;


import com.example.tgbot1.damion.Clients;
import com.example.tgbot1.damion.Maxsulotlar;



import com.example.tgbot1.damion.Zakazla;
import com.example.tgbot1.repo.ClientsRepo;
import com.example.tgbot1.repo.MaxsulotlatRepo;

import com.example.tgbot1.repo.ZakazlaRepo;
import lombok.SneakyThrows;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.*;
import java.io.File;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    String token="1953870980:AAFPRRdhB-UKlRg5-hfnqi0JzH4s7fafFjE";
    String username="botDem_bot";


    @Autowired
    private MaxsulotlatRepo maxsulotlatRepo;
    @Autowired
    private ZakazlaRepo zakazlaRepo;
    private static Message me;
    @Autowired
    private ClientsRepo clientsRepo;


    public String getBotUsername() {
        return username;
    }


    public String getBotToken() {
        return token;
    }

    //////textla
    String maxsulot_tn = "Maxsulotlarni tanlang:";
    String maxsulot_tn_rus = "Выберите товаров";

    String nastroyka = "Nimani sozlaymiz?";
    String nastroyka_rus = "Что настроить?";

    String qabul_ql = "Buyurtmangiz qabul qlindi! Biz siz bilan qisa vaqtlarda aloqaga chqamiz.";
    String qabul_ql_rus = "Ваш заказ успешно оформлен! Мы свяжемся с вами в ближайшее время.";

    String savatchaga_q = "Savatchaga q\'oshildi!";
    String savatchaga_q_rus = "Добавлено в корзину!";

    String def_uz = "Quyidagi buyruqlardan birini tanlang !!!";
    String def_rus = "Пожалуйста выберите из комманд !!!";

    String kontakt_q = "Raqamingiz qo\'shildi!";
    String kotakt_q_rus = "Ваш номер успешно добавлен";

    String oformit = "Tovarlarni rasmiylashtirish ";
    String oformit_rus = "Оформить заказ";

    String nomer = "Raqamni yuborish";
    String nomer_rus = "Отправить номер";

    String ortga = "Ortga";
    String ortga_rus = "Назад";

    String tovarla = "Tovarlar";
    String tovarla_rus = "Товары";

    String korzina = "Savatcha";
    String korzina_rus = "Корзинка";

    String sozlama = "Sozlamalar";
    String sozlama_rus = "Настройки";

    String nomer_yub = "Iltimos telefon raqamingizni yuboring:";
    String nomer_yub_rus = "Пожалуйста отправьте номер";

    String getkontakt = "Raqamni yuborish";
    String getkontakt_rus = "Отправить номер";

    String osebe="☎️Kontatlarimiz";
    String osebe_rus="☎️Наши Контакты";
    /////textla tugadi

    Zakazla zakazla;
    Clients clients;
    List<Clients>clientsList;

    @SneakyThrows
    public void onUpdateReceived(Update update) {


        Message messege = update.getMessage();



        if (messege != null && messege.hasText()) {
            switch (messege.getText()) {

                case "\uD83C\uDDFA\uD83C\uDDFFO\'zbek":

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        x.setTil(true);
                        clientsRepo.save(x);
                       System.out.println(x.isTil()?"salom":"privet");
                       sendMsg(messege, "Siz o'zbek tilini tanladingiz");
                    }
                    break;

                case "Рус\uD83C\uDDF7\uD83C\uDDFA":

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        x.setTil(false);
                        clientsRepo.save(x);
                       System.out.println(x.isTil()?"2salom":"2privet");
                    }
                    sendMsg(messege, "Siz rus tilini tanladingiz");
                    break;

                case "\uD83C\uDDFA\uD83C\uDDFFTil/Язык\uD83C\uDDF7\uD83C\uDDFA":
                    sendMsgSTil(messege);
                    break;

                case "/start":

                    sendMsgStart(messege);
                    break;

                case "\uD83D\uDCE6Товары":
                    sendImageFromUrl(messege.getChatId().toString(), messege.getChatId(),messege);
                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsgTovarlar(messege,x.isTil()?maxsulot_tn_rus:maxsulot_tn);
                    }
                    // sendMsgTovarlar(messege, "Maxsulotlarni tanlang");

                    break;

                case "\uD83D\uDCE6Tovarlar":

                        sendImageFromUrl(messege.getChatId().toString(), messege.getChatId(),messege);
                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsgTovarlar(messege,x.isTil()?maxsulot_tn_rus:maxsulot_tn);
                    }
                       // sendMsgTovarlar(messege, "Maxsulotlarni tanlang");

                    break;

                case "⚙Sozlamalar":
                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsgSetting(messege, x.isTil()?"Nimani sozlaymiz?":"Что настроит?");
                    }
                    break;

                case "⚙Настройки":
                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsgSetting(messege, x.isTil()?"Nimani sozlaymiz?":"Что настроит?");
                    }
                    break;

                case "\uD83D\uDED2Savatcha":
                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsgSavatcha(messege, x.isTil()?"Siz tanlagan maxsulotlar":"Товары которие выберали:");
                    }

                    break;

                case "\uD83D\uDED2Корзинка":
                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsgSavatcha(messege, x.isTil()?"Siz tanlagan maxsulotlar":"Товары которие выберали:");
                    }


                    break;

                case "☎️Kontatlarimiz":
                    sendMsg(messege,"Bizning telefon raqamimiz: +99890-992-22-98");
                    sendLocation(messege);
                    break;
                case "☎️Наши Контакты":
                    sendMsg(messege,"Наш номер телефона: +99890-992-22-98");
                    sendLocation(messege);
                    break;

                case "ortga":
                    sendMsg(messege, "...");
                    break;
                case "назад":
                    sendMsg(messege, "...");
                    break;

                case "Zakazni oformit qlish":
                    List<Zakazla> list = zakazlaRepo.findAllByChatidAndStatus(messege.getChatId().toString(), false);
                    for (Zakazla x : list) {
                        sendgroup(x);
                        x.setStatus(true);
                        zakazlaRepo.save(x);
                    }
                    sendMsg(messege, "qabul boldi");
                    break;

                case "Оформить заказ":
                    List<Zakazla> list2 = zakazlaRepo.findAllByChatidAndStatus(messege.getChatId().toString(), false);
                    for (Zakazla x : list2) {
                        sendgroup(x);
                        x.setStatus(true);
                        zakazlaRepo.save(x);
                    }
                    sendMsg(messege, "qabul boldi");
                    break;

                case "1":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(1);
                    zakazlaRepo.save(zakazla);

                    sendImageFromUrl(messege.getChatId().toString(), messege.getChatId(),messege);
                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;

                case "2":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(2);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;

                case "3":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(3);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;
                case "4":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(4);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;

                case "5":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(5);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;
                case "6":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(6);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;

                case "7":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(7);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;
                case "8":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(8);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;

                case "9":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);
                    zakazla.setSoni(9);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }

                    break;
                case "10":

                    System.out.println(messege.getChatId().toString() + " chat id\n" +
                            me.getMessageId() + " messegeid");

                    zakazla = zakazlaRepo.findAllByChatidAndSoni(messege.getChatId().toString(), 0);

                    zakazla.setSoni(10);
                    zakazlaRepo.save(zakazla);

                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?savatchaga_q:savatchaga_q_rus);
                    }
                    break;

                default:

                    List<Zakazla> list1 = zakazlaRepo.findAllByChatidAndSoniAndStatus(messege.getChatId().toString(), 0, false);
                    if (list1.size() > 0) {
                        for (Zakazla x : list1) {
                            zakazlaRepo.delete(x);
                        }
                    }
                    clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                    for (Clients x:clientsList){
                        sendMsg(messege,x.isTil()?def_uz:def_rus);
                    }

                    break;

            }

        }
        else if (update.hasCallbackQuery()) {
            Message messages = update.getCallbackQuery().getMessage();
            me = messages;
            CallbackQuery callbackQuery = update.getCallbackQuery();

            switch (callbackQuery.getData()) {

                case "/savatchaga":
                    String caption = messages.getCaption().toUpperCase();
                    BufferedReader reader = new BufferedReader(new StringReader(caption));

                    long id = Integer.parseInt(reader.readLine());

                    Maxsulotlar maxsulotlar = maxsulotlatRepo.findAllById(id);

                    Zakazla zakazla1 = new Zakazla(maxsulotlar.getId(),
                            messages.getChat().getFirstName(),
                            messages.getChatId().toString(),
                            maxsulotlar.getImage(),
                            messages.getMessageId(),
                            maxsulotlar.getNarxi(), false, maxsulotlar.getNomi());

                    zakazlaRepo.save(zakazla1);


                    sendMsgZakaz(messages, maxsulotlar.getNomi() + " sonini tanlang:");
                    break;

                case "/delete":
                    break;

                default:

                    List<Zakazla>list=zakazlaRepo.findByChatidAndModelname(messages.getChatId().toString(),
                            callbackQuery.getData());


                    zakazlaRepo.delete(list.get(0));

                    sendMsgSavatcha(messages, "Siz tanlagan maxsulotlar");

                    break;


            }

        }


        else if (messege.getContact() != null) {
            Contact contact = messege.getContact();
            clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
            if (clientsList.size()==0) {
                clients = new Clients(messege.getChat().getFirstName(), contact.getPhoneNumber(), messege.getChatId().toString());
                clientsRepo.save(clients);
                System.out.println(contact.getPhoneNumber() + " kontakt qoshildi");
                clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
                for (Clients x:clientsList){
                    sendMsg(messege,x.isTil()?kontakt_q:kotakt_q_rus);
                }
            }
            else {
                sendMsg(messege, "Raqamingiz qabul qlindi !");

            }

        }

    }

    private void sendMsgStart(Message message) {
        SendMessage sendMessageStart = new SendMessage();
        sendMessageStart.enableMarkdown(true);
        sendMessageStart.setChatId(message.getChatId().toString());
        sendMessageStart.setReplyToMessageId(message.getMessageId());


        //clientsList=clientsRepo.findAllByChatId(message.getChatId().toString());
        //for (Clients x:clientsList){

                sendMessageStart.setText("Raqamingizni yuboring!Пожалуйста отправьте номер!");
       // }


        setButtonsStart(sendMessageStart,message);
        try {
            execute(sendMessageStart);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsgSTil(Message message) {
        SendMessage sendMessageTil = new SendMessage();
        sendMessageTil.enableMarkdown(true);
        sendMessageTil.setChatId(message.getChatId().toString());
        sendMessageTil.setReplyToMessageId(message.getMessageId());

        clientsList=clientsRepo.findAllByChatId(message.getChatId().toString());
        for (Clients x:clientsList){
            sendMessageTil.setText(x.isTil()?"Iltimos tilni tanlang":"Пожалуйста выберите язык");
        }


        setButtonsTil(sendMessageTil);
        try {
            execute(sendMessageTil);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendLocation(Message message) throws TelegramApiException {
         Double latitude=41.30669;
         Double longitude=69.175656;
         SendLocation  location=new SendLocation();
         location.setLatitude(latitude);
         location.setLongitude(longitude);
         location.setChatId(message.getChatId().toString());
         execute(location);
    }

    private void setButtonsTil(SendMessage sendMessageStart) {
        ReplyKeyboardMarkup replyKeyboardMarkupTil = new ReplyKeyboardMarkup();
        sendMessageStart.setReplyMarkup(replyKeyboardMarkupTil);
        replyKeyboardMarkupTil.setSelective(true);
        replyKeyboardMarkupTil.setResizeKeyboard(true);
        replyKeyboardMarkupTil.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowArrayListSavatcha = new ArrayList<>();
        KeyboardRow keyboardRowSavatcha = new KeyboardRow();
        KeyboardButton keyboardButton1 = new KeyboardButton();
        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton1.setText("\uD83C\uDDFA\uD83C\uDDFFO\'zbek");
        keyboardButton2.setText("Рус\uD83C\uDDF7\uD83C\uDDFA");

        keyboardRowSavatcha.add(keyboardButton1);
        keyboardRowSavatcha.add(keyboardButton2);

        keyboardRowArrayListSavatcha.add(keyboardRowSavatcha);
        replyKeyboardMarkupTil.setKeyboard(keyboardRowArrayListSavatcha);
    }

    private void setButtonsStart(SendMessage sendMessageStart,Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkupStart = new ReplyKeyboardMarkup();
        sendMessageStart.setReplyMarkup(replyKeyboardMarkupStart);
        replyKeyboardMarkupStart.setSelective(true);
        replyKeyboardMarkupStart.setResizeKeyboard(true);
        replyKeyboardMarkupStart.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowArrayListSavatcha = new ArrayList<>();
        KeyboardRow keyboardRowSavatcha = new KeyboardRow();
        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setRequestContact(true);

        //clientsList=clientsRepo.findAllByChatId(message.getChatId().toString());
        //for (Clients x:clientsList){
            keyboardButton1.setText("\uD83D\uDCF1 Raqamni yuboring");
        //}



        keyboardRowSavatcha.add(keyboardButton1);

        keyboardRowArrayListSavatcha.add(keyboardRowSavatcha);
        replyKeyboardMarkupStart.setKeyboard(keyboardRowArrayListSavatcha);
    }

    private void sendgroup(Zakazla zakazla) {

        try {
            SendPhoto sendPhotoRequest = new SendPhoto();
            InputFile inputFile = new InputFile();
            List<Clients> clients = clientsRepo.findAllByName(zakazla.getName());


            File file = new File(zakazla.getImage());
            String nomi = zakazla.getName();
            int soni = zakazla.getSoni();
            int narx = zakazla.getNarxi() * zakazla.getSoni();

            inputFile.setMedia(file);
            sendPhotoRequest.setChatId("-570515044");
            sendPhotoRequest.setPhoto(inputFile);
            sendPhotoRequest.setCaption("Tovar id raqami: \t" + zakazla.getModelid() + "\n" +
                    "Klient ismi: " + nomi + "\nTovar soni " + soni + "\n" +
                    "Tovar narxi: " + zakazla.getNarxi() + "\n" +
                    "Umimiy summa\uD83D\uDCB0:" + narx + "\n" +
                    "Kleint raqami: " + clients.get(0).getNumber());

            execute(sendPhotoRequest);


        } catch (TelegramApiException e) {
            e.printStackTrace();

        }


    }

    public void sendImageFromUrl(String chatId, long ChatId,Message message) throws IOException {

        List<Maxsulotlar> list = (List<Maxsulotlar>) maxsulotlatRepo.findAll();

        try {
            SendPhoto sendPhotoRequest = new SendPhoto();
            InputFile inputFile = new InputFile();


            for (Maxsulotlar x : list) {
                File file = new File(x.getImage());
                String nomi = x.getNomi();
                String info = x.getUsr();
                int narx = x.getNarxi();

                inputFile.setMedia(file);
                sendPhotoRequest.setChatId(chatId);
                sendPhotoRequest.setPhoto(inputFile);
                sendPhotoRequest.setCaption(x.getId() + "\n " + nomi + "\n" + info + "\n" + "\uD83D\uDCB0" + narx);

                sendPhotoRequest.setReplyMarkup(inlineButton(message));
                execute(sendPhotoRequest);

            }

        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    public void sendMsg(Message message, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        System.out.println(message.getChatId());
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {

            setButtons(sendMessage,message);
            execute(sendMessage);


        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    private void sendMsgSavatcha(Message messege, String text) throws TelegramApiException {

        SendMessage sendMessageSavatcha = new SendMessage();
        SendMessage sendMessageSavatchakey = new SendMessage();

        sendMessageSavatcha.enableMarkdown(true);
        sendMessageSavatcha.setChatId(messege.getChatId().toString());
        sendMessageSavatcha.setReplyToMessageId(messege.getMessageId());

        sendMessageSavatchakey.enableMarkdown(true);
        sendMessageSavatchakey.setChatId(messege.getChatId().toString());
        sendMessageSavatchakey.setReplyToMessageId(messege.getMessageId());


        try {

            setButtonsSavatcha(sendMessageSavatcha, messege);
            execute(sendMessageSavatcha);
            setButtonsSavatchakey(sendMessageSavatchakey,messege);


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void setButtonsSavatchakey(SendMessage sendMessageSavatcha, Message messege) throws TelegramApiException {

        ReplyKeyboardMarkup replyKeyboardMarkupSavatcha = new ReplyKeyboardMarkup();

        replyKeyboardMarkupSavatcha.setSelective(true);
        replyKeyboardMarkupSavatcha.setResizeKeyboard(true);
        replyKeyboardMarkupSavatcha.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowArrayListSavatcha = new ArrayList<>();
        KeyboardRow keyboardRowSavatcha = new KeyboardRow();

        clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
        for (Clients x:clientsList){
            keyboardRowSavatcha.add(new KeyboardButton(x.isTil()?oformit:oformit_rus));
            keyboardRowSavatcha.add(new KeyboardButton(x.isTil()?ortga:ortga_rus));
        }

        keyboardRowArrayListSavatcha.add(keyboardRowSavatcha);
        replyKeyboardMarkupSavatcha.setKeyboard(keyboardRowArrayListSavatcha);

        sendMessageSavatcha.setReplyMarkup(replyKeyboardMarkupSavatcha);
        sendMessageSavatcha.setText("...");
        execute(sendMessageSavatcha);

    }

    private void setButtonsSettings(SendMessage sendMessageSavatcha,Message message) {

        ReplyKeyboardMarkup replyKeyboardMarkupSavatcha = new ReplyKeyboardMarkup();
        sendMessageSavatcha.setReplyMarkup(replyKeyboardMarkupSavatcha);
        replyKeyboardMarkupSavatcha.setSelective(true);
        replyKeyboardMarkupSavatcha.setResizeKeyboard(true);
        replyKeyboardMarkupSavatcha.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowArrayListSavatcha = new ArrayList<>();
        KeyboardRow keyboardRowSavatcha = new KeyboardRow();

        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setRequestContact(true);
        clientsList=clientsRepo.findAllByChatId(message.getChatId().toString());
        for (Clients x:clientsList){

            keyboardButton1.setText("\uD83D\uDCF1 "+(x.isTil()?getkontakt:getkontakt_rus));
            keyboardRowSavatcha.add(keyboardButton1);
            keyboardRowSavatcha.add(new KeyboardButton("\uD83C\uDDFA\uD83C\uDDFFTil/Язык\uD83C\uDDF7\uD83C\uDDFA"));
            keyboardRowSavatcha.add(new KeyboardButton(x.isTil()?ortga:ortga_rus));

        }

        keyboardRowArrayListSavatcha.add(keyboardRowSavatcha);
        replyKeyboardMarkupSavatcha.setKeyboard(keyboardRowArrayListSavatcha);


    }

    private void setButtonsSavatcha(SendMessage sendMessageSavatcha, Message message) throws TelegramApiException {

        int sum = 0;

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();

        ///////*tugadi*\\\\\\\\

        List<Zakazla> list = zakazlaRepo.findAllByChatidAndStatus(message.getChatId().toString(), false);

        int w = 0;
        while (list.size() > w) {

            /////****\\\\\\\
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

            System.out.println(list.get(w).getModelname());

            inlineKeyboardButton.setText(list.get(w).getModelname() + " ❌");

            inlineKeyboardButton.setCallbackData(list.get(w).getModelname());

            inlineButtons.add(Collections.singletonList(inlineKeyboardButton));

            ///////////****\\\\\\\\\\\\\\\\\\\\\

            sum = sum + (list.get(w).getSoni() * list.get(w).getNarxi());

            w++;
        }

        clientsList=clientsRepo.findAllByChatId(message.getChatId().toString());
        for (Clients x:clientsList){
            sendMessageSavatcha.setText(sum + (x.isTil()?oformit:oformit_rus));
        }


        inlineKeyboardMarkup.setKeyboard(inlineButtons);

        sendMessageSavatcha.setReplyMarkup(inlineKeyboardMarkup);

    }

    private void sendMsgSetting(Message messege, String text) {
        SendMessage sendMessageSavatcha = new SendMessage();
        sendMessageSavatcha.enableMarkdown(true);
        sendMessageSavatcha.setChatId(messege.getChatId().toString());
        sendMessageSavatcha.setReplyToMessageId(messege.getMessageId());

        clientsList=clientsRepo.findAllByChatId(messege.getChatId().toString());
        for (Clients x:clientsList){
            sendMessageSavatcha.setText(x.isTil()?nastroyka:nastroyka_rus);
        }



        setButtonsSettings(sendMessageSavatcha,messege);
        try {
            execute(sendMessageSavatcha);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsgTovarlar(Message messege, String text) {

    }

    public void setButtons(SendMessage sendMessage,Message message) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFistRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        KeyboardRow keyboardFourRow = new KeyboardRow();

        clientsList=clientsRepo.findAllByChatId(message.getChatId().toString());
        for (Clients x:clientsList){
            keyboardFistRow.add(new KeyboardButton("\uD83D\uDCE6"+(x.isTil()?tovarla:tovarla_rus)));
            keyboardSecondRow.add(new KeyboardButton("\uD83D\uDED2"+(x.isTil()?korzina:korzina_rus)));
            keyboardThirdRow.add(new KeyboardButton("⚙"+(x.isTil()?"Sozlamalar":"Настройки")));
            keyboardFourRow.add(new KeyboardButton(x.isTil()?osebe:osebe_rus));
        }


        keyboardRowList.add(keyboardFistRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardThirdRow);
        keyboardRowList.add(keyboardFourRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    private void sendMsgZakaz(Message messege, String text) {

        SendMessage sendMessageZakaz = new SendMessage();
        sendMessageZakaz.enableMarkdown(true);
        sendMessageZakaz.setChatId(messege.getChatId().toString());
        sendMessageZakaz.setReplyToMessageId(messege.getMessageId());
        sendMessageZakaz.setText(text);
        try {

            setButtonsZakaz(sendMessageZakaz);
            execute(sendMessageZakaz);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void setButtonsZakaz(SendMessage sendMessageZakaz) {

        ReplyKeyboardMarkup replyKeyboardMarkupSavatcha = new ReplyKeyboardMarkup();
        sendMessageZakaz.setReplyMarkup(replyKeyboardMarkupSavatcha);
        replyKeyboardMarkupSavatcha.setSelective(true);
        replyKeyboardMarkupSavatcha.setResizeKeyboard(true);
        replyKeyboardMarkupSavatcha.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowArrayListSavatcha = new ArrayList<>();
        KeyboardRow keyboardRowSavatcha = new KeyboardRow();
        KeyboardRow brinchi = new KeyboardRow();
        KeyboardRow ikkinchi = new KeyboardRow();
        KeyboardRow uchunchi = new KeyboardRow();
        KeyboardRow tortinchi = new KeyboardRow();

        brinchi.add("1");
        brinchi.add("2");
        brinchi.add("3");
        ikkinchi.add("4");
        ikkinchi.add("5");
        ikkinchi.add("6");
        uchunchi.add("7");
        uchunchi.add("8");
        uchunchi.add("9");

        tortinchi.add("⬅️Ortga");

        keyboardRowArrayListSavatcha.add(keyboardRowSavatcha);
        keyboardRowArrayListSavatcha.add(brinchi);
        keyboardRowArrayListSavatcha.add(ikkinchi);
        keyboardRowArrayListSavatcha.add(uchunchi);
        keyboardRowArrayListSavatcha.add(tortinchi);
        replyKeyboardMarkupSavatcha.setKeyboard(keyboardRowArrayListSavatcha);


    }




    public InlineKeyboardMarkup inlineButton(Message message) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtons2 = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();

        clientsList=clientsRepo.findAllByChatId(message.getChatId().toString());
        for (Clients x:clientsList){
            inlineKeyboardButton4.setText(x.isTil()?"Savarchaga":"В корзину");
        }



        inlineKeyboardButton4.setCallbackData("/savatchaga");


        inlineKeyboardButtonList2.add(inlineKeyboardButton4);


        inlineButtons2.add(inlineKeyboardButtonList2);


        List<List<InlineKeyboardButton>> rowlist = new ArrayList<>();

        rowlist.add(inlineKeyboardButtonList2);

        inlineKeyboardMarkup.setKeyboard(rowlist);


        return inlineKeyboardMarkup;
    }


}

