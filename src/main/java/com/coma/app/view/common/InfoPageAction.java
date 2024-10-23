package com.coma.app.view.common;

import org.springframework.ui.Model;

public class InfoPageAction {

	public String execute(Model model) {
		model.addAttribute("msg", "페이지 오류");
		model.addAttribute("path", "MAINPAGEACTION.do");
		return "info";
	}

}
