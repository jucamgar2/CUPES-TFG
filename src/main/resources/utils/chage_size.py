from PIL import Image
import os


input_folder = r"C:\Users\guaje\OneDrive\Escritorio\Escudos"

output_folder = r"C:\Users\guaje\OneDrive\Escritorio\Escudos4"

new_size = (2000, 2000)
color = (255, 255, 255)

if not os.path.exists(output_folder):
    os.makedirs(output_folder)

images = os.listdir(input_folder)

for image in images:
    if image.lower().endswith(".png"):
        try:
            image_png = Image.open(os.path.join(input_folder, image))
            image_rdm = image_png.resize(new_size)
            new_image = Image.new("RGB", new_size, color)
            new_image.paste(image_rdm, (0, 0), image_rdm)
            image_jpg = os.path.splitext(image)[0] + ".jpg"
            output_route = os.path.join(output_folder, image_jpg)
            new_image.save(output_route, "JPEG")
            print(f"Imagen redimensionada: {image} -> {image_jpg}")
        except Exception as e:
            print(f"Error al procesar {image}: {str(e)}")
            continue
print("Proceso completado.")