package com.example.tgbot1.controller;





import com.example.tgbot1.damion.Maxsulotlar;


import com.example.tgbot1.damion.Zakazla;
import com.example.tgbot1.repo.MaxsulotlatRepo;


import com.example.tgbot1.repo.ZakazlaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
public class MainController   {
    @Value("${upload.folder}")
    private String uploadPath;



    @Autowired
    private  MaxsulotlatRepo maxsulotlatRepo;





    @GetMapping
    public String greeting(Map<String,Object> model) throws IOException {

       Iterable<Maxsulotlar>maxsulotlars=maxsulotlatRepo.findAll();
      model.put("maxsulotlar",maxsulotlars);

    return "main";
    }
    @GetMapping("index")
    public String main(){
        return "index";
    }


    @PostMapping("/add")
    public String add(Map<String, Object> model,
                      @RequestParam String nomi,
                      @RequestParam String info,
                      @RequestParam int narxi,
                      @RequestParam("img") MultipartFile file) throws IOException {

       // String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Maxsulotlar maxsulotlar=new Maxsulotlar(nomi,info,narxi);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            long size1= file.getSize();
            String type = file.getContentType();

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename =file.getOriginalFilename();
                       file.transferTo(new File(uploadPath + "/" + resultFilename));

            maxsulotlar.setImage(uploadPath+"/"+resultFilename);
        }

        maxsulotlatRepo.save(maxsulotlar);

        System.out.println("Maxsulot qoshildi!!!");


        return "redirect:/";
    }
    @GetMapping("delete")
    public String delete(@RequestParam long id){

      maxsulotlatRepo.deleteById(id);


        return "redirect:/";
    }






    /** File *
    @GetMapping("/photo/{hashId}")
    public HttpEntity<?> images(@PathVariable String hashId,
                                HttpServletRequest request) throws MalformedURLException {
        FileStorage fileStorage=fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename\""+ URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(fileStorage.getUploadPath()));
    }
     */



}







