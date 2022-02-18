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

import com.zjc.po.Tag;
import com.zjc.service.TagsService;





/**
 * 标签页面控制器
 * @author 周金城
 *
 */
@Controller
@RequestMapping("/amin")
public class TagsController {
	
	@Autowired
	private TagsService tagsService;
	
	@GetMapping("/atags")
	public String list(@PageableDefault(size =10,sort = {"id"},direction =Sort.Direction.DESC)
						Pageable pageable,Model model) {
		model.addAttribute("page", tagsService.listTag(pageable));		
		return "amin/atags";
	}
	
	@GetMapping("/atags/{id}/input")
	public String editInput(@PathVariable Long id,Model model) {		
		 model.addAttribute("tag",tagsService.getTag(id));
		return "amin/atagsInput";
	}
	
	
	@GetMapping("/atags/input")
	public String input(Model model) {
		 model.addAttribute("tag", new Tag());
		return "amin/atagsInput";
	}
	
	@PostMapping("/atags")
	public String post(@Valid Tag tag,BindingResult result,RedirectAttributes attributes) {
		
		
		Tag t1=tagsService.getTagByName(tag.getName());
						
		if(t1!=null) {
			result.rejectValue("name","nameError","不能添加重复的标签");
		}
		
		if(result.hasErrors()) {
			return "amin/atagsInput";
		}
		Tag t=tagsService.saveTag(tag);
		if(t==null) {
			attributes.addFlashAttribute("message", "新增失败");
		}else {
			attributes.addFlashAttribute("message", "新增成功");
		}
		return "redirect:/amin/atags";
	}
	
	@PostMapping("/atags/{id}")
	public String editPost(@Valid Tag tag,BindingResult result,
							@PathVariable Long id,RedirectAttributes attributes) {
		
		Tag t1=tagsService.getTagByName(tag.getName());
		
		if(t1!=null) {
		result.rejectValue("name","nameError","不能添加重复的标签");
		}
		
		if(result.hasErrors()) {
			return "amin/atagsInput";
		}
		Tag t=tagsService.updateTag(id, tag);
		if(t==null) {
			attributes.addFlashAttribute("message", "更新失败");
		}else {
			attributes.addFlashAttribute("message", "更新成功");
		}
		return "redirect:/amin/atags";
	}
	
	@GetMapping("/atags/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes attributes) {
		tagsService.deleteTag(id);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/amin/atags";
	}
}
