export async function createFile(localFilePath: string, newFileName: string): Promise<File> {
    let response = await fetch(localFilePath);
    let data = await response.blob();
    let metadata = {
        type: 'image/png'
    };
    let file = new File([data], newFileName, metadata);
    return file;
}


export function readFile(): any {
    const reader = new FileReader();

    return (file: File) =>
        new Promise<string>((resolve) => {
            reader.onload = () => {
                resolve(reader.result as string);
            };
            reader.readAsDataURL(file);
        });
}

export function compareArrays(array1: Array<any>, array2: Array<any>): boolean {
    return array1.length === array2.length && array1.every((value, index) => value === array2[index]);
}