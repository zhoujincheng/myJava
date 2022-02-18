package com.zjc.web.amin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zjc.po.Type;
import com.zjc.service.TypeService;



/**
 * 分类页面控制器
 * @author 周金城
 *
 */
@Controller
@RequestMapping("/amin")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	@GetMapping("/types")
	public String list(@PageableDefault(size =10,sort = {"id"},direction =Sort.Direction.DESC)
						Pageable pageable,Model model) {
		model.addAttribute("page", typeService.listType(pageable));		
		return "amin/types";
	}
	
	@GetMapping("/types/{id}/input")
	public String editInput(@PathVariable Long id,Model model) {		
		 model.addAttribute("type",typeService.getType(id));
		return "amin/typesInput";
	}
	
	
	@GetMapping("/types/input")
	public String input(Model model) {
		 model.addAttribute("type", new Type());
		return "amin/typesInput";
	}
	
	@PostMapping("/types")
	public String post(@Valid Type type,BindingResult result,RedirectAttributes attributes) {
		
		Type t1=typeService.getTypeByName(type.getName());
		
		if(t1!=null) {
		result.rejectValue("name","nameError","不能添加重复的分类");
		}
		
		if(result.hasErrors()) {
			return "amin/typesInput";
		}
		Type t=typeService.saveType(type);
		if(t==null) {
			attributes.addFlashAttribute("message", "新增失败");
		}else {
			attributes.addFlashAttribute("message", "新增成功");
		}
		return "redirect:/amin/types";
	}
	
	@PostMapping("/types/{id}")
	public String editPost(@Valid Type type,BindingResult result,
							@PathVariable Long id,RedirectAttributes attributes) {
		
		Type t1=typeService.getTypeByName(type.getName());
		
		if(t1!=null) {
		result.rejectValue("name","nameError","不能添加重复的分类");
		}
		
		if(result.hasErrors()) {
			return "amin/typesInput";
		}
		Type t=typeService.updateType(id, type);
		if(t==null) {
			attributes.addFlashAttribute("message", "更新失败");
		}else {
			attributes.addFlashAttribute("message", "更新成功");
		}
		return "redirect:/amin/types";
	}
	
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes attributes) {
		typeService.deleteType(id);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/amin/types";
	}
}
