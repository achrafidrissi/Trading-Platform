import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "@radix-ui/react-avatar";

const Portfolio = () => {
  return (
    <div className="p-5 lg:px-20">
      <h1 className="font-bold text-3xl pb-5">Portfolio</h1>
      <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="">Assets</TableHead>
          <TableHead>PRICE</TableHead>
          <TableHead>UNIT</TableHead>
          <TableHead>CHANGE</TableHead>
          <TableHead>CHANGE(%)</TableHead>
          <TableHead className="text-right">VOLUME</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {[1,1,1,1,1,1,1,1,1,1].map((item, index) => <TableRow key={index}>
          <TableCell className="font-medium flex items-center gap-2">
            <Avatar className="-z-50">
              <AvatarImage
              className="w-[30px]"
              src="https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400" />
            </Avatar>
            <span>Bitcoin</span>
          </TableCell>
          <TableCell>BTC</TableCell>
          <TableCell>9124463121</TableCell>
          <TableCell>1364881428323</TableCell>
          <TableCell>-0.20009</TableCell>
          <TableCell className="text-right">$69249</TableCell>
        </TableRow> )}
      </TableBody>
    </Table>
    </div>
  )
}

export default Portfolio
