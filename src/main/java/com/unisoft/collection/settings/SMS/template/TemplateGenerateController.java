package com.unisoft.collection.settings.SMS.template;

import com.unisoft.collection.settings.SMS.generate.GeneratedSMS;
import com.unisoft.collection.settings.SMS.generate.GeneratedSMSRepository;
import com.unisoft.collection.settings.SMS.smsType.SMSEntity;
import com.unisoft.collection.settings.SMS.smsType.SMSRepository;
import com.unisoft.collection.settings.SMS.smsType.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/collection/generator")
public class TemplateGenerateController {

    @Autowired
    private TemplateGenerateService templateGenerateService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private TemplateGenerateRepository templateGenerateRepository;

    @Autowired
    private SMSRepository smsRepository;

    @Autowired
    private GeneratedSMSRepository generatedSMSRepository;

    @GetMapping(value = "/create")
    public String viewAddPage(Model model) {

        TemplateGenerate templateGenerate = new TemplateGenerate();
        model.addAttribute("templateGenerate", templateGenerate);

        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("smsEntityList", smsEntityList);


        return "collection/settings/sms/smsgenerator/create";
    }

    @PostMapping(value = "/save-generator")
    public String saveSMSGenerator(Model model, TemplateGenerate templateGenerate){

        templateGenerateService.saveGenerate(templateGenerate);
        return "redirect:/collection/generator/list";

    }

    @GetMapping(value="/list")
    public String findAllSms(Model model){

        List<TemplateGenerate>  generate = templateGenerateService.findAll();
        model.addAttribute("generate", generate);

        return "collection/settings/sms/smsgenerator/generator";
    }

    @GetMapping("/edit")
    public String editGeneration(@RequestParam Long id, Model model){
        TemplateGenerate templateGenerate = templateGenerateRepository.findTempById(id);
        model.addAttribute("templateGenerate", templateGenerate);

        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("smsEntityList", smsEntityList);

        return "collection/settings/sms/smsgenerator/create";
    }

    @GetMapping("/view")
    public String viewGenerator(@RequestParam Long id, Model model){
        TemplateGenerate templateGenerate = templateGenerateService.findTemGenById(id);
        model.addAttribute("templateGenerate", templateGenerate);

        List<TemplateGenerate>  generate = templateGenerateService.findAll();
        model.addAttribute("generate", generate);

//        List<SMSEntity> smsEntityList = smsService.findAll();
//
//        model.addAttribute("smsEntityList", smsEntityList);
        SMSEntity smsEntity = new SMSEntity();
        model.addAttribute("smsEntity", smsEntity);

        return "collection/settings/sms/smsgenerator/show";

    }


    //@GetMapping(value = "/generatesms")
    @GetMapping("/generatesms")
    public String generatesms(@RequestParam(value = "accountNo") List<String> loanViewModelForSMS,
                              @RequestParam(value = "smsType") Long smsType, Model model){
        String sms = "";

        List<GeneratedSMS> generatedSMS = new ArrayList<>();

        SMSEntity smsEntity = smsService.findSmsById(smsType);
        TemplateGenerate templateGenerate = templateGenerateRepository.findTemGenBySmsTypeId(smsType);

        for(String acc : loanViewModelForSMS){
            String[] content = acc.split(":");
            sms = templateGenerate.getMassege();
            sms = sms.replace("{{F.accountNo}}",content[0]);
            sms = sms.replace("{{F.installmentAmount}}",content[3]);
            sms = sms.replace("{{F.nextEmiDate}}",content[4]);
            sms = sms.replace("{{F.currentMonth}}",content[5]);
            sms = sms.replace("{{F.productName}}",content[2]);
            GeneratedSMS generatedSMS1 = new GeneratedSMS(smsEntity,sms,content[0],content[1]);
            generatedSMS.add(generatedSMS1);
        }
        generatedSMSRepository.saveAll(generatedSMS);

        return generatedSmsList(model);//"redirect:/collection/generator/generatedsmslist";
    }

    @GetMapping("/generatedsmslist")
    public String generatedSmsList(Model model){

        model.addAttribute("generatedSMS",generatedSMSRepository.findAll());

        return "collection/settings/SMS/generatedsms/gensms";
    }

    @GetMapping("/byId")
    public ResponseEntity<Map<String,Object>> getById(@RequestParam(value = "id") Long id){

        Map<String,Object> map = new HashMap<>();
        map.put("message",templateGenerateRepository.findTemGenBySmsTypeId(id).getMassege());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}