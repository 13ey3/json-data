package id.mitraprimautama.smsgateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.mitraprimautama.smsgateway.helper.Dates;


@Controller
@RequestMapping(value = {"", "/", "/dashboard"})
public class DashboardController {

  // @Autowired
  private Dates tgl = new Dates();
  // private String tgl_sekarang;

  @GetMapping(value = {"", "/"})
  public String index(Model model) {
    model.addAttribute("tanggal", tgl.getTanggal());
    model.addAttribute("jam", tgl.getWaktu());
    return "dashboard/index";
  }
}