package com.jose.api.restController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jose.api.models.Servicios;
import com.jose.api.repositories.ServicesRepository;

@RestController
public class GreetingController {
	
	/*contraseña heroku fifajuan0208$*/
	@Autowired
	private final ServicesRepository servicesRepository;

	public GreetingController(ServicesRepository repo) {
		// TODO Auto-generated constructor stub
		this.servicesRepository=repo;
	}
	
	@GetMapping("/")
	public String index() {
		System.out.println("Entra");
		return "Hola";
	}

	@GetMapping("/allServices")
	public List<Servicios> getAllServices() {
		List<Servicios> servicios=servicesRepository.findAll();
		return servicios;
	}
	@GetMapping("/getAService/{id}")
	public Servicios getService(@PathVariable long id) {
		try {
			return servicesRepository.findById(id).orElseThrow (() -> new Exception());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@DeleteMapping("/deleteService/{id}")
	public HashMap<String, Object> deleteAService(@PathVariable long id) {
		HashMap<String, Object> map= new HashMap<String, Object>();
		
		if(servicesRepository.findById(id).isPresent()) {
			servicesRepository.deleteById(id);
			map.put("status", 200);
			map.put("message", "Delete Success");
			return map;	
		}else {
			map.put("status", 404);
			map.put("message", "Delete fail");
			return map;
		}
		
	}
	@PutMapping("/updateStatus/{id}")
	public HashMap<String, Object> updateStatus(@PathVariable long id, @ModelAttribute Servicios updateService) {
		Optional<Servicios> old= servicesRepository.findById(id);
		HashMap<String, Object> mapa= new HashMap<String, Object>();
		if(old!=null) {
			updateService.setId(id);
			updateService.setFecha(old.get().getFecha());
			
			servicesRepository.save(updateService);
			
			mapa.put("status", 200);
			mapa.put("message", "This service has been updated");
			return mapa;
		}else {
			mapa.put("message", "Not found Service");
			mapa.put("status", 404);
			return mapa;
		}
	}
	@PostMapping("/insertService")
	public HashMap<String, Object> insertService(@ModelAttribute Servicios newService) {
		HashMap<String, Object> mapeo= new HashMap<>();
		newService.setFecha(new Date());
		/*try {
			//newService.setData(compressBytes(file.getBytes()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		mapeo.put("service", servicesRepository.save(newService));
		mapeo.put("at",new Date());
		return mapeo;
	}

	@PostMapping("insertImageService/{id}")
	public HashMap<String,Object> insertImageService 
	(@PathVariable("id") long id,@RequestParam("picture") MultipartFile file) {
		HashMap<String, Object> mapa= new HashMap<>();
		try {
			System.out.println("id recibido "+id);
			Optional<Servicios> service=servicesRepository.findById(id);
			service.get().setData(file.getBytes());
			Servicios service2=servicesRepository.save(service.get());
			mapa.put("service",service2);
			mapa.put("at", new Date());
			mapa.put("data",service2.getData());
			System.out.println(service2.toString());
			return mapa;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mapa.put("service", "Got a problem");
			mapa.put("at", new Date());
			return mapa;
		}
		
		
		
	}
	@GetMapping("img/{id}")
	public byte[] get(@PathVariable long id) {
		Optional<Servicios> servi= servicesRepository.findById(id);
		System.out.println("--> "+servi.get().getData());
		return servi.get().getData();
	}
	@PostMapping("/insertService2")
	public HashMap<String, Object> insertService2(@RequestBody HashMap<String, Object> servicio) {
		HashMap<String, Object> mapa= new HashMap<>();
				System.out.println("SErvice "+servicio.toString());
				System.out.println("Object: "+servicio.get("nombrePerro"));
				Servicios servicioInsert= new Servicios();
				servicioInsert.setNombrePerro((String)servicio.get("nombrePerro"));
				servicioInsert.setResponsable((String)servicio.get("responsable"));
				servicioInsert.setComentarios((String)servicio.get("comentarios"));
				servicioInsert.setPrecio((int)servicio.get("precio"));
				servicioInsert.setServicio((String)servicio.get("servicio"));
				servicioInsert.setNumTel((String)servicio.get("numTel"));
				servicioInsert.setFecha(new Date());
				mapa.put("at",new Date()); 
				Servicios serv=servicesRepository.save(servicioInsert);
				mapa.put("service",serv);
				mapa.put("data", serv.getData());
		return mapa;
	}
	@GetMapping("/error")
	public String error() {
		return "somethig went wrong!!!";
	}
	public static byte[] compressBytes(byte[] data) {
	    Deflater deflater = new Deflater();
	    deflater.setInput(data);
	    deflater.finish();
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	    byte[] buffer = new byte[1024];
	    while (!deflater.finished()) {
	      int count = deflater.deflate(buffer);
	      outputStream.write(buffer, 0, count);
	    }
	    try {
	      outputStream.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return outputStream.toByteArray();
	  }
}
