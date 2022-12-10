package com.main.serviceIMPL;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Contact;
import com.main.exception.ContactNotFoundException;
import com.main.repository.ContactRepository;
import com.main.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository repo;

	@Override
	public String saveContact(Contact contact) {
		try {
			repo.save(contact);

			return "Contact Saved Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed while Saving Contact ";
		}

	}

	@Override
	public List<Contact> getAllContact() {

		List<Contact> contactList = (List<Contact>) repo.findAll();
		return contactList;
	}

	@Override
	public Contact getContactById(Integer id) {

		Optional<Contact> singleContact = repo.findById(id);
		if (singleContact.isPresent()) {
			return singleContact.get();
		} else {
			System.out.println("Not Found");
			return null;
		}
	}

	@Override
	public String updateContact(Contact contact) {
		contact.setId(contact.getId());
		contact.setName(contact.getName());
		contact.setEmail(contact.getEmail());
		contact.setPhone(contact.getPhone());
		repo.save(contact);
		return "Updated Successfully";
	}

	@Override
	public List<Contact> deleteContactById(Integer id) {

		repo.delete(repo.findById(id).orElseThrow(() -> new ContactNotFoundException(
				String.format("--%s NOT HAVING % d--", Contact.class.getName(), id))));
		return getAllContact();
	}

}
